package site.iplease.irdserver.domain.reserve.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto

interface ReserveValidator {
    fun validate(dto: ReserveDto): Mono<Unit>
}
