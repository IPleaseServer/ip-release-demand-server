package site.iplease.irdserver.domain.query.controller

import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.query.config.DataQueryProperty
import site.iplease.irdserver.domain.query.response.PagableReleaseDemandQueryResponse
import site.iplease.irdserver.domain.query.response.ReleaseDemandQueryResponse
import site.iplease.irdserver.domain.query.service.ReleaseDemandQueryService

@RestController
@RequestMapping("/api/v1/demand/release/query")
class IpReleaseDemandQueryController(
    private val dataQueryProperty: DataQueryProperty,
    private val releaseDemandQueryService: ReleaseDemandQueryService
) {
    @GetMapping("/all")
    fun getAllReleaseDemand(@RequestParam page: Int): Mono<ResponseEntity<PagableReleaseDemandQueryResponse>> =
        PageRequest.of(page, dataQueryProperty.all.pageSize).toMono()
            .flatMap { releaseDemandQueryService.getAllReleaseDemand(it) }
            .map { PagableReleaseDemandQueryResponse(it) }
            .map { ResponseEntity.ok(it) }

    @GetMapping("/issuer")
    fun getReleaseDemandByIssuerId(@RequestParam page: Int, @RequestParam issuerId: Long): Mono<ResponseEntity<PagableReleaseDemandQueryResponse>> =
        PageRequest.of(page, dataQueryProperty.all.pageSize).toMono()
            .flatMap { releaseDemandQueryService.getAllReleaseDemandByIssuerId(it, issuerId) }
            .map { PagableReleaseDemandQueryResponse(it) }
            .map { ResponseEntity.ok(it) }

    @GetMapping("/{demandId}")
    fun getReleaseDemandById(@PathVariable demandId: Long): Mono<ResponseEntity<ReleaseDemandQueryResponse>> =
        releaseDemandQueryService.getReleaseDemandById(demandId)
            .map { ReleaseDemandQueryResponse(it) }
            .map { ResponseEntity.ok(it) }
}