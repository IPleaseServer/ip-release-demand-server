package site.iplease.irdserver.domain.reserve.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.entity.Reserve
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse

@Component
class ReserveConverterImpl: ReserveConverter {
    override fun toEntity(dto: ReserveDto): Mono<Reserve> =
        Unit.toMono().map { Reserve(
            id = dto.id,
            assignIpId = dto.assignIpId,
            issuerId = dto.issuerId,
            releaseAt = dto.releaseAt,
        ) }

    override fun toDto(entity: Reserve): Mono<ReserveDto> =
        Unit.toMono().map { ReserveDto(
            id = entity.id,
            assignIpId = entity.assignIpId,
            issuerId = entity.issuerId,
            releaseAt = entity.releaseAt,
        ) }

    override fun toDto(request: CreateIpReleaseReserveRequest, issuerId: Long): Mono<ReserveDto> =
        Unit.toMono().map { ReserveDto(
            id = 0,
            assignIpId = request.assignIpId,
            issuerId = issuerId,
            releaseAt = request.releaseAt
        ) }

    override fun toCreateIpReleaseReserveResponse(dto: ReserveDto): Mono<CreateIpReleaseReserveResponse> =
        Unit.toMono().map { CreateIpReleaseReserveResponse(reserveId = dto.id) }
}