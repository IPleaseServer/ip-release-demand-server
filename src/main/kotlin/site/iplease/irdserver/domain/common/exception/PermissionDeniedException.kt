package site.iplease.irdserver.domain.common.exception

import site.iplease.irdserver.global.error.IpleaseError

data class PermissionDeniedException(private val errorDetail: String): RuntimeException("$ERROR_MESSAGE - $errorDetail"), IpleaseError {
    companion object {
        private const val ERROR_MESSAGE = "해당 작업을 수행할 권한이 없습니다!"
    }
    override fun getErrorMessage(): String = ERROR_MESSAGE
    override fun getErrorDetail(): String = errorDetail
}