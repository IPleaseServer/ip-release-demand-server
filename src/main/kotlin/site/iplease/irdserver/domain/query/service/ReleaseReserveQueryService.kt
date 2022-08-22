package site.iplease.irdserver.domain.query.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto

interface ReleaseReserveQueryService {
    fun getAllReleaseReserve(it: PageRequest): Mono<Page<ReserveDto>>
    fun getAllReleaseReserveByIssuerId(it: PageRequest, issuerId: Long): Mono<Page<ReserveDto>>
    fun getReleaseReserveById(reserveId: Long): Mono<ReserveDto>
}
