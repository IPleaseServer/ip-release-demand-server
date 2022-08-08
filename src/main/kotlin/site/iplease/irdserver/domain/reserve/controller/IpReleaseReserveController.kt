package site.iplease.irdserver.domain.reserve.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse
import site.iplease.irdserver.domain.reserve.service.ReserveService
import site.iplease.irdserver.domain.reserve.util.ReserveConverter

@RestController
@RequestMapping("/api/v1/reserve/release")
class IpReleaseReserveController(
    private val reserveService: ReserveService,
    private val reserveConverter: ReserveConverter
) {
    @PostMapping
    fun createIpReleaseReserve(@RequestBody request: CreateIpReleaseReserveRequest)
    : Mono<ResponseEntity<CreateIpReleaseReserveResponse>> =
        reserveConverter.toDto(request)
            .flatMap { reserveService.addReserve(it) }
            .flatMap { reserveConverter.toCreateIpReleaseReserveResponse(it) }
            .map { ResponseEntity.ok(it) }
}