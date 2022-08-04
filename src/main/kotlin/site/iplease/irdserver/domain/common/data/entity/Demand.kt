package site.iplease.irdserver.domain.common.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table
data class Demand(
    @Id val id: Long,
    val assignIpId: Long,
    val issuerId: Long
)