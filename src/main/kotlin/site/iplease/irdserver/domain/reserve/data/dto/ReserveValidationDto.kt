package site.iplease.irdserver.domain.reserve.data.dto

import site.iplease.irdserver.infra.account.data.type.PermissionType
import java.time.LocalDate

//예약을 검증하기위한 정보를 담은 Dto
data class ReserveValidationDto(
    val issuerId: Long,
    val issuerPermission: PermissionType,
    val reserveId: Long,
    val assignIpId: Long,
    val releaseAt: LocalDate
)