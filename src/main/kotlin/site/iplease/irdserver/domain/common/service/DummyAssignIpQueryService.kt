package site.iplease.irdserver.domain.common.service

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.infra.assign_ip.data.dto.AssignIpDto
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService

@Component
class DummyAssignIpQueryService: AssignIpQueryService {
    override fun existsById(assignIpId: Long): Mono<Boolean> = true.toMono()

    override fun findById(assignIpId: Long): Mono<AssignIpDto> = AssignIpDto(assignIpId, "127.0.0.1", 3, 1).toMono()
}