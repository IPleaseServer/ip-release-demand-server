package site.iplease.irdserver.global.accept.data.message

import java.time.LocalDate

data class AssignIpCreateMessage (val id: Long, val expireAt: LocalDate)