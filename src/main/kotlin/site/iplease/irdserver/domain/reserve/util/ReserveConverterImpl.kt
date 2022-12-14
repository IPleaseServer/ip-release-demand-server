package site.iplease.irdserver.domain.reserve.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.dto.ReserveValidationDto
import site.iplease.irdserver.domain.reserve.data.entity.Reserve
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CancelIpReleaseReserveResponse
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse
import site.iplease.irdserver.infra.account.data.type.PermissionType
import java.time.LocalDate

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

    override fun toDto(id: Long, issuerId: Long): Mono<ReserveDto> =
        Unit.toMono().map { ReserveDto(
            id = id,
            assignIpId = -1,
            issuerId = issuerId,
            releaseAt = LocalDate.MIN,
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

    override fun toCancelIpReleaseReserveResponse(dto: ReserveDto): Mono<CancelIpReleaseReserveResponse> =
        Unit.toMono().map { CancelIpReleaseReserveResponse(reserveId = dto.id) }

    override fun toValidationDto(dto: ReserveDto): Mono<ReserveValidationDto> =
        Unit.toMono().map { ReserveValidationDto(
            reserveId = dto.id,
            assignIpId = dto.assignIpId,
            issuerId = dto.issuerId,
            releaseAt = dto.releaseAt,
            issuerPermission = PermissionType.UNKNOWN,
        ) }

    override fun toValidationDto(dto: ReserveDto, permission: PermissionType): Mono<ReserveValidationDto> =
        Unit.toMono().map { ReserveValidationDto(
            reserveId = dto.id,
            assignIpId = dto.assignIpId,
            issuerId = dto.issuerId,
            releaseAt = dto.releaseAt,
            issuerPermission = permission,
        ) }
}