package site.iplease.irdserver.domain.demand.dto

import site.iplease.irdserver.domain.demand.data.type.DemandStatusType

data class DemandDto(
    val id: Long,
    val assignIpId: Long,
    val issuerId: Long,
    val status: DemandStatusType
)