package site.iplease.irdserver.domain.query.controller

import org.springframework.data.domain.PageRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.query.config.DataQueryProperty
import site.iplease.irdserver.domain.query.response.PagableReleaseReserveQueryResponse
import site.iplease.irdserver.domain.query.response.ReleaseReserveQueryResponse
import site.iplease.irdserver.domain.query.service.ReleaseReserveQueryService

@RestController
@RequestMapping("/api/v1/reserve/release/query")
class IpReleaseReserveQueryController(
    private val dataQueryProperty: DataQueryProperty,
    private val releaseReserveQueryService: ReleaseReserveQueryService
) {
    @GetMapping("/all")
    fun getAllReleaseReserve(@RequestParam page: Int): Mono<ResponseEntity<PagableReleaseReserveQueryResponse>> =
        PageRequest.of(page, dataQueryProperty.all.pageSize).toMono()
            .flatMap { releaseReserveQueryService.getAllReleaseReserve(it) }
            .map { PagableReleaseReserveQueryResponse(it) }
            .map { ResponseEntity.ok(it) }

    @GetMapping("/issuer")
    fun getReleaseReserveByIssuerId(@RequestParam page: Int, @RequestParam issuerId: Long): Mono<ResponseEntity<PagableReleaseReserveQueryResponse>> =
        PageRequest.of(page, dataQueryProperty.all.pageSize).toMono()
            .flatMap { releaseReserveQueryService.getAllReleaseReserveByIssuerId(it, issuerId) }
            .map { PagableReleaseReserveQueryResponse(it) }
            .map { ResponseEntity.ok(it) }

    @GetMapping("/{reserveId}")
    fun getReleaseReserveById(@PathVariable reserveId: Long): Mono<ResponseEntity<ReleaseReserveQueryResponse>> =
        releaseReserveQueryService.getReleaseReserveById(reserveId)
            .map { ReleaseReserveQueryResponse(it) }
            .map { ResponseEntity.ok(it) }
}