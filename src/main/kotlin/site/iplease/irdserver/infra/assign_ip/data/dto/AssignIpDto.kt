package site.iplease.irdserver.infra.assign_ip.data.dto

data class AssignIpDto (
    val id: Long,
    val ip: String,
    val assigneeId: Long,
    val assignerId: Long
)