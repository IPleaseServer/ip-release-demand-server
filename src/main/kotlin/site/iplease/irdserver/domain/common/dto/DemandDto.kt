package site.iplease.irdserver.domain.common.dto

import site.iplease.irdserver.domain.common.data.type.DemandStatusType

data class DemandDto(
    val id: Long,
    val assignIpId: Long,
    val issuerId: Long,
    val status: DemandStatusType
)