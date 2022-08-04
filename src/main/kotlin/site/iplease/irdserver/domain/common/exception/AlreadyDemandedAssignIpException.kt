package site.iplease.irdserver.domain.common.exception

import site.iplease.irdserver.global.error.IpleaseError
data class AlreadyDemandedAssignIpException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object {
        private const val ERROR_MESSAGE = "이미 신청된 할당IP입니다!"
    }
    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}