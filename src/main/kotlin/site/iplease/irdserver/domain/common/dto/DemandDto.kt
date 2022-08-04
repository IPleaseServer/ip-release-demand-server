package site.iplease.irdserver.domain.common.dto

data class DemandDto(
    val id: Long,
    val assignIpId: Long,
    val issuerId: Long
)