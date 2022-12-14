package site.iplease.irdserver.global.reserve.service

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto

interface ReserveService {
    fun addReserve(dto: ReserveDto): Mono<ReserveDto>
    fun cancelReserve(dto: ReserveDto): Mono<ReserveDto>

}