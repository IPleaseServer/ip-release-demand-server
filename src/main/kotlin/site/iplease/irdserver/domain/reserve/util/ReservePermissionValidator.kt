package site.iplease.irdserver.domain.reserve.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.type.ReservePermission

interface ReservePermissionValidator {
    fun validate(permission: ReservePermission, issuerId: Long, assignIpId: Long): Mono<Unit>
}
