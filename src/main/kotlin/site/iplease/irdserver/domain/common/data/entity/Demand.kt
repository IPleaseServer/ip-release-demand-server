package site.iplease.irdserver.domain.common.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import site.iplease.irdserver.domain.common.data.type.DemandStatusType

@Table
data class Demand(
    @Id val id: Long,
    val assignIpId: Long,
    val issuerId: Long,
    val status: DemandStatusType
)