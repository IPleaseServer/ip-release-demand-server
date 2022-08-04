package site.iplease.irdserver.domain.common.exception

class AlreadyDemandedAssignIpException(message: String): RuntimeException("이미 신청된 할당IP입니다! - $message")