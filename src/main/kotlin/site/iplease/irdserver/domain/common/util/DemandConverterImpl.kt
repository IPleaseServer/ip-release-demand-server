package site.iplease.irdserver.domain.common.util

import org.springframework.stereotype.Component
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.common.data.entity.Demand
import site.iplease.irdserver.domain.common.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.domain.common.data.response.AcceptReleaseDemandResponse
import site.iplease.irdserver.domain.common.data.response.CancelReleaseDemandResponse
import site.iplease.irdserver.domain.common.data.response.CreateReleaseDemandResponse
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.data.type.DemandStatusType

@Component
class DemandConverterImpl: DemandConverter {
    override fun toEntity(dto: DemandDto): Mono<Demand> =
        Unit.toMono().map { Demand(
            id = dto.id,
            assignIpId = dto.assignIpId,
            issuerId = dto.issuerId,
            status = dto.status,
        ) }

    override fun toDto(demandId: Long): Mono<DemandDto> =
        Unit.toMono().map { DemandDto(
            id = demandId,
            assignIpId = -1,
            issuerId = -1,
            status = DemandStatusType.CREATE,
        ) }

    override fun toDto(entity: Demand): Mono<DemandDto> =
        Unit.toMono().map { DemandDto(
            id = entity.id,
            assignIpId = entity.assignIpId,
            issuerId = entity.issuerId,
            status = entity.status,
        ) }

    override fun toDto(issuerId: Long, demandId: Long): Mono<DemandDto> =
        Unit.toMono().map { DemandDto(
            id = demandId,
            assignIpId = -1,
            issuerId = issuerId,
            status = DemandStatusType.CREATE,
        ) }

    override fun toDto(request: CreateReleaseDemandRequest): Mono<DemandDto> =
        Unit.toMono().map { DemandDto(
            id = 0,
            assignIpId = request.assignIpId,
            issuerId = request.issuerId,
            status = DemandStatusType.CREATE,
        ) }

    override fun toCreateReleaseDemandResponse(dto: DemandDto): Mono<CreateReleaseDemandResponse> =
        Unit.toMono().map { CreateReleaseDemandResponse(demandId = dto.id) }

    override fun toCancelReleaseDemandResponse(demandId: Long): Mono<CancelReleaseDemandResponse> =
        Unit.toMono().map { CancelReleaseDemandResponse(removedDemandId = demandId) }

    override fun toAcceptReleaseDemandResponse(demandId: Long): Mono<AcceptReleaseDemandResponse> =
        Unit.toMono().map { AcceptReleaseDemandResponse(demandId) }
}