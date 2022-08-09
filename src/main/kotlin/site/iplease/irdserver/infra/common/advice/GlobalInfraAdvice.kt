package site.iplease.irdserver.infra.common.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.global.error.ErrorStatus
import site.iplease.irdserver.infra.assign_ip.exception.ApiException

@RestControllerAdvice(annotations = [RestController::class])
class GlobalInfraAdvice {
    @ExceptionHandler(ApiException::class)
    fun handle(e: ApiException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.internalServerError()
            .body(ErrorResponse(
                status = ErrorStatus.API_ERROR,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()
}