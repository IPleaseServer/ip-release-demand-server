package site.iplease.irdserver.domain.query.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.demand.dto.DemandDto

interface ReleaseDemandQueryService {
    fun getAllReleaseDemand(page: PageRequest): Mono<Page<DemandDto>>
    fun getAllReleaseDemandByIssuerId(page: PageRequest, issuerId: Long): Mono<Page<DemandDto>>
    fun getReleaseDemandById(demandId: Long): Mono<DemandDto>
}
