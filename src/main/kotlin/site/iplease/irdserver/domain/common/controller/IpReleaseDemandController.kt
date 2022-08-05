package site.iplease.irdserver.domain.common.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.domain.common.data.response.CreateReleaseDemandResponse
import site.iplease.irdserver.domain.common.data.response.CancelReleaseDemandResponse
import site.iplease.irdserver.domain.common.service.DemandService
import site.iplease.irdserver.domain.common.util.DemandConverter

@RestController
@RequestMapping("/api/v1/demand/release")
class IpReleaseDemandController(
    private val demandConverter: DemandConverter,
    private val demandService: DemandService
) {
    @PostMapping //TODO Gateway Server에서 보내주는 X-Authorization-Id 로 issuer 판독하도록 로직변경
    fun createReleaseDemand(@RequestBody request: CreateReleaseDemandRequest): Mono<ResponseEntity<CreateReleaseDemandResponse>> =
        demandConverter.toDto(request)
            .flatMap { demandService.addDemand(it) }
            .flatMap { demandConverter.toCreateReleaseDemandResponse(it) }
            .map { ResponseEntity.ok(it) }

    @DeleteMapping("/{demandId}")
    fun cancelReleaseDemand(@RequestHeader("X-Authorization-Id") issuerId: Long,
                            @PathVariable demandId: Long
    ): Mono<ResponseEntity<CancelReleaseDemandResponse>> =
        demandConverter.toDto(issuerId = issuerId, demandId = demandId)
            .flatMap { demandService.cancelDemand(it) }
            .flatMap { demandConverter.toCancelReleaseDemandResponse(it) }
            .map { ResponseEntity.ok(it) }
}