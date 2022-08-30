package site.iplease.irdserver.domain.query.service

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.entity.Reserve
import site.iplease.irdserver.domain.reserve.repository.ReserveRepository
import site.iplease.irdserver.domain.reserve.util.ReserveConverter

@Service
class ReleaseReserveQueryServiceImpl(
    private val reserveRepository: ReserveRepository,
    private val reserveConverter: ReserveConverter
): ReleaseReserveQueryService {
    override fun getAllReleaseReserve(page: PageRequest): Mono<Page<ReserveDto>> = reserveRepository.findBy(page).convert()
    override fun getAllReleaseReserveByIssuerId(page: PageRequest, issuerId: Long): Mono<Page<ReserveDto>> = reserveRepository.findAllByIssuerId(page, issuerId).convert()
    override fun getReleaseReserveById(reserveId: Long): Mono<ReserveDto> = reserveRepository.findById(reserveId).flatMap { reserveConverter.toDto(it) }
    override fun getReleaseReserveByAssignIpId(page: PageRequest, assignIpId: Long): Mono<Page<ReserveDto>> = reserveRepository.findAllByAssignIpId(page, assignIpId).convert()

    private fun Flux<Reserve>.convert(): Mono<Page<ReserveDto>> =
        flatMap { reserveConverter.toDto(it) }
            .collectList()
            .map { PageImpl(it) }
}