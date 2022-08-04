package site.iplease.irdserver.infra.assign_ip.service

import reactor.core.publisher.Mono
import site.iplease.irdserver.infra.assign_ip.data.dto.AssignIpDto

interface AssignIpQueryService {
    fun existsById(assignIpId: Long): Mono<Boolean>
    fun findById(assignIpId: Long): Mono<AssignIpDto>
}
