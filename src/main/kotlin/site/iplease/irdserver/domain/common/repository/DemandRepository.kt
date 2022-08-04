package site.iplease.irdserver.domain.common.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.data.entity.Demand

interface DemandRepository: ReactiveCrudRepository<Demand, Long> {
    fun existsByAssignIpId(assignIpId: Long): Mono<Boolean>
}