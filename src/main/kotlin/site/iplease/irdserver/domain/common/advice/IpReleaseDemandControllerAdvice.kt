package site.iplease.irdserver.domain.common.advice

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.common.controller.IpReleaseDemandController
import site.iplease.irdserver.domain.common.exception.AlreadyDemandedAssignIpException
import site.iplease.irdserver.domain.common.exception.PermissionDeniedException
import site.iplease.irdserver.domain.common.exception.UnknownAssignIpException
import site.iplease.irdserver.domain.common.exception.UnknownDemandException
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.global.error.ErrorStatus

@RestControllerAdvice(basePackageClasses = [IpReleaseDemandController::class])
class IpReleaseDemandControllerAdvice {
    @ExceptionHandler(AlreadyDemandedAssignIpException::class)
    fun handle(e: AlreadyDemandedAssignIpException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
        .body(ErrorResponse(
            status = ErrorStatus.ALREADY_DEMANDED_ASSIGN_IP,
            message = e.getErrorMessage(),
            detail = e.getErrorDetail()
        )).toMono()

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

    @ExceptionHandler(UnknownDemandException::class)
    fun handle(e: UnknownDemandException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.UNKNOWN_DEMAND,
                message = e.getErrorMessage(),
                detail = e.getErrorDetail()
            )).toMono()
}