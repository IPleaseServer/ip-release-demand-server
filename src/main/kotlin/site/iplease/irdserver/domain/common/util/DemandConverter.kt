package site.iplease.irdserver.domain.common.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.data.entity.Demand
import site.iplease.irdserver.domain.common.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.domain.common.data.response.CreateReleaseDemandResponse
import site.iplease.irdserver.domain.common.dto.DemandDto

interface DemandConverter {
    fun toEntity(dto: DemandDto): Mono<Demand>
    fun toDto(request: Demand): Mono<DemandDto>
    fun toDto(request: CreateReleaseDemandRequest): Mono<DemandDto>
    fun toResponse(resultDto: DemandDto): Mono<CreateReleaseDemandResponse>

}
