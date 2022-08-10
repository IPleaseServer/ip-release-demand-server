package site.iplease.irdserver.domain.reserve.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveValidationDto
import site.iplease.irdserver.domain.reserve.data.type.ReservePermission
import site.iplease.irdserver.domain.reserve.data.type.ReservePolicyType
import site.iplease.irdserver.domain.reserve.data.type.ReservePolicyType.RESERVE_CANCEL
import site.iplease.irdserver.domain.reserve.data.type.ReservePolicyType.RESERVE_CREATE
import site.iplease.irdserver.domain.reserve.exception.AlreadyReservedAssignIpException
import site.iplease.irdserver.domain.reserve.repository.ReserveRepository
import site.iplease.irdserver.global.common.exception.PermissionDeniedException
import site.iplease.irdserver.global.common.exception.UnknownAssignIpException
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService

@Component
class ReserveValidatorImpl(
    private val reserveRepository: ReserveRepository,
    private val reservePermissionValidator: ReservePermissionValidator,
    private val assignIpQueryService: AssignIpQueryService
): ReserveValidator {
    override fun validate(dto: ReserveValidationDto, type: ReservePolicyType): Mono<Unit> =
        when(type) {
            RESERVE_CREATE ->
                isExistsByAssignIpId(dto.assignIpId, Mono.defer { Mono.error(AlreadyReservedAssignIpException("이미 id가 ${dto.assignIpId}인 할당IP에 대한 해제예약이 존재합니다!")) }, Unit.toMono())
                    .flatMap { isAssignIpExists(dto.assignIpId) }
                    .flatMap { hasPermission(ReservePermission.CREATE, assignIpId =  dto.assignIpId, issuerId = dto.issuerId) }

            RESERVE_CANCEL -> 
                isExistsByAssignIpId(dto.assignIpId, Mono.defer { Mono.error(UnknownAssignIpException("해당 id의 할당IP이 존재하지 않습니다!")) }, Unit.toMono())
                    .flatMap { isOwner(dto.reserveId, dto.issuerId) }
        }

    private fun isOwner(id: Long, issuerId: Long): Mono<Unit> =
        reserveRepository.findById(id).flatMap { reserve ->
            if(reserve.issuerId == issuerId) Unit.toMono()
            else Mono.error(PermissionDeniedException("IP할당해제예약취소는 신청의 소유자만 가능합니다! - 예약 ID: $id, 예약자 ID: ${reserve.issuerId}, 요청자 ID: $issuerId"))
        }

    private fun isExistsByAssignIpId(assignIpId: Long, whenExists: Mono<Unit>, orElse: Mono<Unit>): Mono<Unit> =
        reserveRepository.existsByAssignIpId(assignIpId).flatMap { isExists -> if(isExists) whenExists else orElse }

    private fun isAssignIpExists(assignIpId: Long): Mono<Unit> =
        assignIpQueryService.existsById(assignIpId).flatMap { isExists ->
            if(isExists) Unit.toMono()
            else Mono.error(UnknownAssignIpException("할당IP id가 ${assignIpId}인 할당IP가 존재하지 않습니다!"))
        }

    private fun hasPermission(permission: ReservePermission, assignIpId: Long, issuerId: Long): Mono<Unit> =
        reservePermissionValidator.validate(permission, assignIpId = assignIpId, issuerId = issuerId)
}
