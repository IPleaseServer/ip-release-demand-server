package site.iplease.irdserver.domain.demand.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import site.iplease.irdserver.domain.demand.data.type.DemandStatusType

@Table
data class Demand(
    @Id val id: Long,
    val assignIpId: Long,
    val issuerId: Long,
    val status: DemandStatusType
)