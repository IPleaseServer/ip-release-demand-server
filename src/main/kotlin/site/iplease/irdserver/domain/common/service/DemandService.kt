package site.iplease.irdserver.domain.common.service

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.dto.DemandDto

interface DemandService {
    fun addDemand(dto: DemandDto): Mono<DemandDto>
    fun cancelDemand(dto: DemandDto): Mono<Long>
    fun acceptDemand(dto: DemandDto): Mono<DemandDto>
}
