package site.iplease.irdserver.domain.reserve.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CancelIpReleaseReserveResponse
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse
import site.iplease.irdserver.domain.reserve.data.type.ReservePolicyType
import site.iplease.irdserver.global.reserve.service.ReserveService
import site.iplease.irdserver.domain.reserve.util.ReserveConverter
import site.iplease.irdserver.domain.reserve.util.ReserveValidator
import site.iplease.irdserver.infra.account.data.type.PermissionType

@RestController
@RequestMapping("/api/v1/reserve/release/command")
class IpReleaseReserveController(
    private val reserveService: ReserveService,
    private val reserveConverter: ReserveConverter,
    private val reserveValidator: ReserveValidator
) {
    @PostMapping
    fun createIpReleaseReserve(
        @RequestHeader("X-Authorization-Id") issuerId: Long,
        @RequestBody request: CreateIpReleaseReserveRequest
    ): Mono<ResponseEntity<CreateIpReleaseReserveResponse>> =
        reserveConverter.toDto(request, issuerId)
            .flatMap { reserveDto ->
                reserveConverter.toValidationDto(reserveDto)
                    .flatMap { validationDto -> reserveValidator.validate(validationDto, ReservePolicyType.RESERVE_CREATE) }
                    .map { _ -> reserveDto }
            }.flatMap { reserveService.addReserve(it) }
            .flatMap { reserveConverter.toCreateIpReleaseReserveResponse(it) }
            .map { ResponseEntity.ok(it) }

    @DeleteMapping("/{reserveId}")
    fun cancelIpReleaseReserve(
        @RequestHeader("X-Authorization-Id") issuerId: Long,
        @RequestHeader("X-Authorization-Permission") permission: PermissionType,
        @PathVariable reserveId: Long
    ): Mono<ResponseEntity<CancelIpReleaseReserveResponse>> =
        reserveConverter.toDto(id = reserveId, issuerId = issuerId)
            .flatMap { reserveDto ->
                reserveConverter.toValidationDto(reserveDto, permission)
                    .flatMap { validationDto -> reserveValidator.validate(validationDto, ReservePolicyType.RESERVE_CANCEL) }
                    .map { _ -> reserveDto }
            }.flatMap { reserveService.cancelReserve(it) }
            .flatMap { reserveConverter.toCancelIpReleaseReserveResponse(it) }
            .map { ResponseEntity.ok(it) }
}