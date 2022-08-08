package site.iplease.irdserver.domain.reserve.data.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table
data class Reserve(
    @Id val id: Long,
    val assignIpId: Long,
    val issuerId: Long,
    val releaseAt: LocalDate
)