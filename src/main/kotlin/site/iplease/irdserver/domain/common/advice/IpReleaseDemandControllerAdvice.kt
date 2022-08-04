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
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.global.error.ErrorStatus

@RestControllerAdvice(basePackageClasses = [IpReleaseDemandController::class])
class IpReleaseDemandControllerAdvice {
    @ExceptionHandler(AlreadyDemandedAssignIpException::class)
    fun handle(e: AlreadyDemandedAssignIpException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
        .body(ErrorResponse(
            status = ErrorStatus.ALREADY_DEMANDED_ASSIGN_IP,
            message = "이미 할당해제신청된 할당IP입니다.",
            detail = e.localizedMessage
        )).toMono()

    @ExceptionHandler(PermissionDeniedException::class)
    fun handle(e: PermissionDeniedException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.PERMISSION_DENIED,
                message = "해당 기능에 접근할 권한이 없습니다.",
                detail = e.localizedMessage
            )).toMono()

    @ExceptionHandler(UnknownAssignIpException::class)
    fun handle(e: UnknownAssignIpException): Mono<ResponseEntity<ErrorResponse>> =
        ResponseEntity.badRequest()
            .body(ErrorResponse(
                status = ErrorStatus.UNKNOWN_ASSIGN_IP,
                message = "존재하지 않는 할당IP입니다.",
                detail = e.localizedMessage
            )).toMono()
}