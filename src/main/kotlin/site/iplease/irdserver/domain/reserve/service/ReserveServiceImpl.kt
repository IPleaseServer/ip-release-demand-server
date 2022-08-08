package site.iplease.irdserver.domain.reserve.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.repository.ReserveRepository
import site.iplease.irdserver.domain.reserve.util.ReserveConverter
import site.iplease.irdserver.domain.reserve.util.ReserveValidator

@Service
class ReserveServiceImpl(
    private val reserveConverter: ReserveConverter,
    private val reserveRepository: ReserveRepository,
    private val reserveValidator: ReserveValidator
) : ReserveService {
    override fun addReserve(dto: ReserveDto): Mono<ReserveDto> =
        reserveValidator.validate(dto)
            .flatMap { reserveConverter.toEntity(dto) }
            .map { it.copy(id = 0) }
            .flatMap { reserveRepository.save(it) }
            .flatMap { reserveConverter.toDto(it) }
}
