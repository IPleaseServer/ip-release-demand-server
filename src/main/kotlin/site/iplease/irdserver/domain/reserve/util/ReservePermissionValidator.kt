package site.iplease.irdserver.domain.reserve.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.type.ReservePermission
import site.iplease.irdserver.infra.account.data.type.PermissionType

interface ReservePermissionValidator {
    fun validate(permission: ReservePermission, issuerId: Long, issuerPermission: PermissionType, assignIpId: Long): Mono<Unit>
}
