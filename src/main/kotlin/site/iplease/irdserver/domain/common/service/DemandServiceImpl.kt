package site.iplease.irdserver.domain.common.service

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.common.data.type.DemandPolicyType
import site.iplease.irdserver.domain.common.data.type.DemandStatusType
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.repository.DemandRepository
import site.iplease.irdserver.domain.common.util.DemandConverter
import site.iplease.irdserver.domain.common.util.DemandValidator

@Component
class DemandServiceImpl(
    private val demandRepository: DemandRepository,
    private val demandValidator: DemandValidator,
    private val demandConverter: DemandConverter
): DemandService {
    override fun addDemand(dto: DemandDto): Mono<DemandDto> =
        demandValidator.validate(dto, DemandPolicyType.DEMAND_CREATE)
            .flatMap { demandConverter.toEntity(dto) }
            .map { it.copy(id=0) }
            .flatMap { demandRepository.save(it) }
            .flatMap { demandConverter.toDto(it) }

    override fun cancelDemand(dto: DemandDto): Mono<Long> =
        demandValidator.validate(dto, DemandPolicyType.DEMAND_CANCEL)
            .flatMap { demandRepository.deleteById(dto.id) }
            .then(dto.id.toMono())

    override fun acceptDemand(dto: DemandDto): Mono<DemandDto> =
        demandValidator.validate(dto, DemandPolicyType.DEMAND_ACCEPT)
            .flatMap { demandRepository.findById(dto.id) }
            .map { it.copy(status = DemandStatusType.ACCEPT) }
            .flatMap { demandRepository.save(it) }
            .flatMap { demandConverter.toDto(it) }
}