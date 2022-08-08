package site.iplease.irdserver.domain.reserve.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse

interface ReserveConverter {
    fun toDto(request: CreateIpReleaseReserveRequest): Mono<ReserveDto>
    fun toCreateIpReleaseReserveResponse(dto: ReserveDto): Mono<CreateIpReleaseReserveResponse>
}
