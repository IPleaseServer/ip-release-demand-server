package site.iplease.irdserver.domain.demand.repository

import org.springframework.data.domain.Pageable
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.demand.data.entity.Demand
import site.iplease.irdserver.domain.demand.data.type.DemandStatusType

interface DemandRepository: ReactiveCrudRepository<Demand, Long> {
    fun findBy(page: Pageable): Flux<Demand>//https://stackoverflow.com/questions/58874827/spring-data-r2dbc-and-pagination
    fun findByIssuerId(page: Pageable, issuerId: Long): Flux<Demand>
    fun existsByAssignIpId(assignIpId: Long): Mono<Boolean>
    fun deleteAllByStatus(status: DemandStatusType): Mono<Void>
}