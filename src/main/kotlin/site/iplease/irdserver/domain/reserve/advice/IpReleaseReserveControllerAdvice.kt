package site.iplease.irdserver.domain.reserve.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.exception.AlreadyReservedAssignIpException
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.global.error.ErrorStatus

@RestControllerAdvice
class IpReleaseReserveControllerAdvice {
    @ExceptionHandler(AlreadyReservedAssignIpException::class)
    fun handle(e: AlreadyReservedAssignIpException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.ALREADY_RESERVED_ASSIGN_IP,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()
}