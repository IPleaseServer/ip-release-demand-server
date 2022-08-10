package site.iplease.irdserver.domain.reserve.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.repository.ReserveRepository
import site.iplease.irdserver.domain.reserve.util.ReserveConverter

@Service
class ReserveServiceImpl(
    private val reserveConverter: ReserveConverter,
    private val reserveRepository: ReserveRepository
) : ReserveService {
    override fun addReserve(dto: ReserveDto): Mono<ReserveDto> =
        reserveConverter.toEntity(dto)
            .map { it.copy(id = 0) }
            .flatMap { reserveRepository.save(it) }
            .flatMap { reserveConverter.toDto(it) }

    override fun cancelReserve(dto: ReserveDto): Mono<ReserveDto> =
        reserveConverter.toEntity(dto)
            .flatMap { reserveRepository.deleteById(dto.id) }
            .then(dto.toMono())
}