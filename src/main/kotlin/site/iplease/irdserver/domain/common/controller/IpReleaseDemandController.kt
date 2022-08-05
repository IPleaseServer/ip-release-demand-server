package site.iplease.irdserver.domain.common.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.domain.common.data.response.CreateReleaseDemandResponse
import site.iplease.irdserver.domain.common.service.DemandService
import site.iplease.irdserver.domain.common.util.DemandConverter

@RestController
@RequestMapping("/api/v1/demand/release")
class IpReleaseDemandController(
    private val demandConverter: DemandConverter,
    private val demandService: DemandService
) {
    @PostMapping
    fun createReleaseDemand(@RequestBody request: CreateReleaseDemandRequest): Mono<ResponseEntity<CreateReleaseDemandResponse>> =
        demandConverter.toDto(request)
            .flatMap { demandService.addDemand(it) }
            .flatMap { demandConverter.toResponse(it) }
            .map { ResponseEntity.ok(it) }
}