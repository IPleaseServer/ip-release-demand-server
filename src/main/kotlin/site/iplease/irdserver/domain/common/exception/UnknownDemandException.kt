package site.iplease.irdserver.domain.common.exception

import site.iplease.irdserver.global.error.IpleaseError

data class UnknownDemandException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object {
        private const val ERROR_MESSAGE = "존재하지 않는 신청입니다!"
    }
    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}