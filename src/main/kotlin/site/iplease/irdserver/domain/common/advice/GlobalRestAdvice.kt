package site.iplease.irdserver.domain.common.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.global.common.exception.PermissionDeniedException
import site.iplease.irdserver.global.common.exception.UnknownAssignIpException
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.global.error.ErrorStatus

@RestControllerAdvice(annotations = [RestController::class])
class GlobalRestAdvice {
    @ExceptionHandler(PermissionDeniedException::class)
    fun handle(e: PermissionDeniedException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.PERMISSION_DENIED,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()

    @ExceptionHandler(UnknownAssignIpException::class)
    fun handle(e: UnknownAssignIpException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.UNKNOWN_ASSIGN_IP,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()
}