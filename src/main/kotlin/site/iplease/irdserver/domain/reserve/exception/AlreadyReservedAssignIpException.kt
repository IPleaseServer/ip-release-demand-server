package site.iplease.irdserver.domain.reserve.exception

import site.iplease.irdserver.global.error.IpleaseError

class AlreadyReservedAssignIpException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object {
        private const val ERROR_MESSAGE = "이미 예약된 할당IP입니다!"
    }
    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}