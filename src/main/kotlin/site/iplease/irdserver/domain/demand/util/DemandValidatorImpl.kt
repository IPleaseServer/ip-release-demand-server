package site.iplease.irdserver.domain.demand.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.demand.data.type.DemandPolicyType
import site.iplease.irdserver.domain.demand.data.type.DemandStatusType
import site.iplease.irdserver.domain.demand.dto.DemandDto
import site.iplease.irdserver.domain.demand.exception.*
import site.iplease.irdserver.domain.demand.repository.DemandRepository
import site.iplease.irdserver.global.common.exception.PermissionDeniedException
import site.iplease.irdserver.global.common.exception.UnknownAssignIpException
import site.iplease.irdserver.infra.account.data.type.PermissionType
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService

@Component
class DemandValidatorImpl(
    private val demandRepository: DemandRepository,
    private val assignIpQueryService: AssignIpQueryService
): DemandValidator {
    override fun validate(dto: DemandDto, policyType: DemandPolicyType): Mono<Unit> =
        when(policyType) {
            DemandPolicyType.DEMAND_CREATE -> isNotExistsByAssignIpId(dto.assignIpId)
                .flatMap { isAssignIpExists(dto.assignIpId) }
                .flatMap { isAssignIpCanAccess(dto.assignIpId, dto.issuerId, dto.issuerPermission) }
            DemandPolicyType.DEMAND_CANCEL -> isExists(dto.id)
                .flatMap { isOwner(dto.id, dto.issuerId) }
            DemandPolicyType.DEMAND_ACCEPT -> isExists(dto.id)
                .flatMap { isCanAccept(dto.id) }
        }

    private fun isCanAccept(id: Long): Mono<Unit> =
        demandRepository.findById(id).flatMap { demand ->
                if(demand.status != DemandStatusType.ACCEPT) Unit.toMono()
                else Mono.error(AlreadyAcceptedDemandException("이미 수락된 신청입니다! - 신청 ID: ${demand.id}"))
            }

    private fun isOwner(id: Long, issuerId: Long): Mono<Unit> =
        demandRepository.findById(id).flatMap { demand ->
            if(demand.issuerId == issuerId) Unit.toMono()
            else Mono.error(PermissionDeniedException("IP할당해제신청취소는 신청의 소유자만 가능합니다! - 신청 ID: $id, 신청자 ID: ${demand.issuerId}, 요청자 ID: $issuerId"))
        }

    private fun isExists(id: Long): Mono<Unit> =
        demandRepository.existsById(id).flatMap { isExists ->
            if(isExists) Unit.toMono()
            else Mono.error(UnknownDemandException("신청ID가 ${id}인 신청이 존재하지 않습니다!"))
        }

    private fun isNotExistsByAssignIpId(assignIpId: Long): Mono<Unit> =
        demandRepository.existsByAssignIpId(assignIpId)
            .flatMap { isExists ->
                if(isExists) Mono.error(AlreadyDemandedAssignIpException("이미 id가 ${assignIpId}인 할당IP에 대한 해제신청이 존재합니다!"))
                else Unit.toMono()
            }

    private fun isAssignIpExists(assignIpId: Long): Mono<Unit> =
        assignIpQueryService.existsById(assignIpId)
            .flatMap { isExists ->
                if(isExists) Unit.toMono()
                else Mono.error(UnknownAssignIpException("할당IP id가 ${assignIpId}인 할당IP가 존재하지 않습니다!"))
            }

    private fun isAssignIpCanAccess(assignIpId: Long, issuerId: Long, issuerPermission: PermissionType): Mono<Unit> =
        assignIpQueryService.findById(assignIpId)
            .flatMap { assignIp ->
                if(assignIp.assigneeId == issuerId || issuerPermission == PermissionType.ADMINISTRATOR) Unit.toMono()
                else Mono.error(PermissionDeniedException("IP할당해제신청은 할당IP의 소유자만 가능합니다! - 할당IP ID: ${assignIpId}, 할당자 ID: ${assignIp.assigneeId}, 요청자 ID: $issuerId"))
            }
}