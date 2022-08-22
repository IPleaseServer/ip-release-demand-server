package site.iplease.irdserver.domain.query.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.demand.data.entity.Demand
import site.iplease.irdserver.domain.demand.dto.DemandDto
import site.iplease.irdserver.domain.demand.repository.DemandRepository
import site.iplease.irdserver.domain.demand.util.DemandConverter

@Service
class ReleaseDemandQueryServiceImpl(
    private val demandRepository: DemandRepository,
    private val demandConverter: DemandConverter
): ReleaseDemandQueryService {
    override fun getAllReleaseDemand(page: PageRequest): Mono<Page<DemandDto>> = demandRepository.findBy(page).convert()

    override fun getAllReleaseDemandByIssuerId(page: PageRequest, issuerId: Long): Mono<Page<DemandDto>> = demandRepository.findByIssuerId(page, issuerId).convert()

    override fun getReleaseDemandById(demandId: Long): Mono<DemandDto> = demandRepository.findById(demandId).flatMap { demandConverter.toDto(it) }

    private fun Flux<Demand>.convert(): Mono<Page<DemandDto>> =
        flatMap { demandConverter.toDto(it) }
            .collectList()
            .map { PageImpl(it) }
}