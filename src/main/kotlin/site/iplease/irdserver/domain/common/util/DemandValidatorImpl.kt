package site.iplease.irdserver.domain.common.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.common.data.type.DemandPolicyType
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.exception.AlreadyDemandedAssignIpException
import site.iplease.irdserver.domain.common.exception.PermissionDeniedException
import site.iplease.irdserver.domain.common.exception.UnknownAssignIpException
import site.iplease.irdserver.domain.common.repository.DemandRepository
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
                .flatMap { isAssignIpOwner(dto.assignIpId, dto.issuerId) }
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

    private fun isAssignIpOwner(assignIpId: Long, issuerId: Long): Mono<Unit> =
        assignIpQueryService.findById(assignIpId)
            .flatMap { assignIp ->
                if(assignIp.assigneeId == issuerId) Unit.toMono()
                else Mono.error(PermissionDeniedException("IP할당해제신청은 할당IP의 소유자만 가능합니다! - 할당IP ID: ${assignIpId}, 할당자 ID: ${assignIp.assigneeId}, 요청자 ID: ${issuerId}"))
            }
}