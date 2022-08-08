package site.iplease.irdserver.domain.reserve.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.type.ReservePolicyType

interface ReserveValidator {
    fun validate(dto: ReserveDto, type: ReservePolicyType): Mono<Unit>
}
