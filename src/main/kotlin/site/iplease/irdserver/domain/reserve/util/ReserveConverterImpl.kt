package site.iplease.irdserver.domain.reserve.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse

@Component
class ReserveConverterImpl: ReserveConverter {
    override fun toDto(request: CreateIpReleaseReserveRequest): Mono<ReserveDto> =
        Unit.toMono().map { ReserveDto(
            id = 0,
            assignIpId = request.assignIpId,
            issuerId = request.issuerId,
            releaseAt = request.releaseAt
        ) }

    override fun toCreateIpReleaseReserveResponse(dto: ReserveDto): Mono<CreateIpReleaseReserveResponse> =
        Unit.toMono().map { CreateIpReleaseReserveResponse(reserveId = dto.id) }
}