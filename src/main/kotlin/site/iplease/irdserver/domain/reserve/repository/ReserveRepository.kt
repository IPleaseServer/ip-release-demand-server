package site.iplease.irdserver.domain.reserve.repository

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.entity.Reserve

interface ReserveRepository: ReactiveCrudRepository<Reserve, Long> {
    fun existsByAssignIpId(assignIpId: Long): Mono<Boolean>
}