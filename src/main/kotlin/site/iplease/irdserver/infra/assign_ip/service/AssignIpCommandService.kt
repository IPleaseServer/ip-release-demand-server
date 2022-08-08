package site.iplease.irdserver.infra.assign_ip.service

import reactor.core.publisher.Mono

interface AssignIpCommandService {
    fun removeAssignIpById(assignIpId: Long): Mono<Unit>
}
