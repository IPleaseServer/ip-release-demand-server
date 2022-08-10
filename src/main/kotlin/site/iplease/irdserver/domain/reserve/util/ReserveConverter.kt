package site.iplease.irdserver.domain.reserve.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.dto.ReserveValidationDto
import site.iplease.irdserver.domain.reserve.data.entity.Reserve
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CancelIpReleaseReserveResponse
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse
import site.iplease.irdserver.infra.account.data.type.PermissionType

interface ReserveConverter {
    fun toEntity(dto: ReserveDto): Mono<Reserve>
    fun toDto(entity: Reserve): Mono<ReserveDto>
    fun toDto(id: Long, issuerId: Long): Mono<ReserveDto>
    fun toDto(request: CreateIpReleaseReserveRequest, issuerId: Long): Mono<ReserveDto>
    fun toCreateIpReleaseReserveResponse(dto: ReserveDto): Mono<CreateIpReleaseReserveResponse>
    fun toCancelIpReleaseReserveResponse(dto: ReserveDto): Mono<CancelIpReleaseReserveResponse>
    fun toValidationDto(dto: ReserveDto): Mono<ReserveValidationDto>
    fun toValidationDto(dto: ReserveDto, permission: PermissionType): Mono<ReserveValidationDto>
}
