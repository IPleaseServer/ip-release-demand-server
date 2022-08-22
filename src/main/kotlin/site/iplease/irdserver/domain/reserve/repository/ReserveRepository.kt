package site.iplease.irdserver.domain.reserve.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.entity.Reserve
import java.time.LocalDate

interface ReserveRepository: ReactiveCrudRepository<Reserve, Long> {
    fun findBy(page: Pageable): Flux<Reserve>
    fun findAllByIssuerId(page: Pageable, issuerId: Long): Flux<Reserve>
    fun existsByAssignIpId(assignIpId: Long): Mono<Boolean>
    fun findAllByReleaseAt(releaseAt: LocalDate): Flux<Reserve>
}