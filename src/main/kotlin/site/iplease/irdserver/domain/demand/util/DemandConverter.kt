package site.iplease.irdserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.demand.data.entity.Demand
import site.iplease.irdserver.domain.demand.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.domain.demand.data.response.AcceptReleaseDemandResponse
import site.iplease.irdserver.domain.demand.data.response.CancelReleaseDemandResponse
import site.iplease.irdserver.domain.demand.data.response.CreateReleaseDemandResponse
import site.iplease.irdserver.domain.demand.dto.DemandDto

interface DemandConverter {
    fun toEntity(dto: DemandDto): Mono<Demand>
    fun toDto(demandId: Long): Mono<DemandDto>
    fun toDto(entity: Demand): Mono<DemandDto>
    fun toDto(issuerId: Long, demandId: Long): Mono<DemandDto>
    fun toDto(request: CreateReleaseDemandRequest): Mono<DemandDto>
    fun toCreateReleaseDemandResponse(dto: DemandDto): Mono<CreateReleaseDemandResponse>
    fun toCancelReleaseDemandResponse(demandId: Long): Mono<CancelReleaseDemandResponse>
    fun toAcceptReleaseDemandResponse(demandId: Long): Mono<AcceptReleaseDemandResponse>
}
