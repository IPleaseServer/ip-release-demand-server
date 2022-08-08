package site.iplease.irdserver.domain.reserve.service

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto

@Service
class ReserveServiceImpl: ReserveService {
    override fun addReserve(dto: ReserveDto): Mono<ReserveDto> =
        TODO("Not yet implemented")
}
