package site.iplease.irdserver.domain.common.service

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.repository.DemandRepository
import site.iplease.irdserver.domain.common.util.DemandValidator

class DemandServiceImpl(
    private val demandRepository: DemandRepository,
    private val demandValidator: DemandValidator
): DemandService {
    override fun addDemand(dto: DemandDto): Mono<DemandDto> {
        TODO("Not yet implemented")
    }
}
