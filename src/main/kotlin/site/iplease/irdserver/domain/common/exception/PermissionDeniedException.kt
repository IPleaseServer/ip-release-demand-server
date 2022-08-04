package site.iplease.irdserver.domain.common.exception

class PermissionDeniedException(message: String): RuntimeException("해당 작업을 수행할 권한이 없습니다! - $message")