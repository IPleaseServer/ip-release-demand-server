package site.iplease.irdserver.domain.reserve.data.dto

import java.time.LocalDate

data class ReserveDto(
    val id: Long,
    val assignIpId: Long,
    val issuerId: Long,
    val releaseAt: LocalDate
)