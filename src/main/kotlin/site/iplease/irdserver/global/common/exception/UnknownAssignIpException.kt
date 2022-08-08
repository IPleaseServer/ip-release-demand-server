package site.iplease.irdserver.global.common.exception

import site.iplease.irdserver.global.error.IpleaseError

data class UnknownAssignIpException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object {
        private const val ERROR_MESSAGE = "존재하지 않는 할당IP입니다!"
    }
    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}