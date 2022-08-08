package site.iplease.irdserver.domain.demand.service

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.demand.dto.DemandDto

interface DemandService {
    fun addDemand(dto: DemandDto): Mono<DemandDto>
    fun cancelDemand(dto: DemandDto): Mono<Long>
    fun acceptDemand(dto: DemandDto): Mono<DemandDto>
}
