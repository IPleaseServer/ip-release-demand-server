package site.iplease.irdserver.domain.reserve.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveValidationDto
import site.iplease.irdserver.domain.reserve.data.type.ReservePolicyType

interface ReserveValidator {
    fun validate(dto: ReserveValidationDto, type: ReservePolicyType): Mono<Unit>
}
