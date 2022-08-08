package site.iplease.irdserver.domain.reserve.data.request

import java.time.LocalDate

data class CreateIpReleaseReserveRequest(val assignIpId: Long, val releaseAt: LocalDate)