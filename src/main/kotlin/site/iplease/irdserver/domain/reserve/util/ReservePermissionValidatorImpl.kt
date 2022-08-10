package site.iplease.irdserver.domain.reserve.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.data.type.ReservePermission
import site.iplease.irdserver.global.common.exception.PermissionDeniedException
import site.iplease.irdserver.infra.account.data.type.PermissionType
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService
import site.iplease.irdserver.domain.reserve.util.ReservePermissionValidatorImpl.ReservePermissionValidateStatus.*

@Component
class ReservePermissionValidatorImpl(
    private val assignIpQueryService: AssignIpQueryService
): ReservePermissionValidator {
    override fun validate(permission: ReservePermission, issuerId: Long, issuerPermission: PermissionType, assignIpId: Long): Mono<Unit> =
        when(permission) {
            //hasPermission.ADMINISTRATOR or isOwner.assignIp
            ReservePermission.CREATE -> hasPermission(issuerPermission = issuerPermission, permission = PermissionType.ADMINISTRATOR)
                .flatMap {
                    if(it != SUCCESS) isOwner(issuerId = issuerId, assignIpId = assignIpId)
                    else it.toMono()
                }.flatMap {
                    if(it == SUCCESS) Unit.toMono()
                    else Mono.error(PermissionDeniedException("할당해제예약추가권한이 없습니다! - 할당IP소유자 또는 최고관리자만 할당IP에 대한 할당해제예약을 추가할 수 있습니다!"))
                }
        }

    private fun hasPermission(issuerPermission: PermissionType, permission: PermissionType) =
        if(issuerPermission == permission) SUCCESS.toMono() else PERMISSION_DENIED.toMono()

    private fun isOwner(issuerId: Long, assignIpId: Long) =
        assignIpQueryService.findById(assignIpId)
            .map {
                if(it.assigneeId == issuerId) SUCCESS
                else NOT_OWNER
            }

    enum class ReservePermissionValidateStatus{
        NOT_OWNER, PERMISSION_DENIED, SUCCESS
    }
}