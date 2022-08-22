package site.iplease.irdserver.domain.demand.dto

import site.iplease.irdserver.domain.demand.data.type.DemandStatusType
import site.iplease.irdserver.infra.account.data.type.PermissionType

data class DemandDto(
    val id: Long,
    val assignIpId: Long,
    val issuerId: Long,
    val issuerPermission: PermissionType,
    val status: DemandStatusType
)