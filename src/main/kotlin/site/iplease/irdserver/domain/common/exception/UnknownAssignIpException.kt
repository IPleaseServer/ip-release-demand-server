package site.iplease.irdserver.domain.common.exception

class UnknownAssignIpException(message: String): RuntimeException("존재하지 않는 할당IP입니다! - $message")