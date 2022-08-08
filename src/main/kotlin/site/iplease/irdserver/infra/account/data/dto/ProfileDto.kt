package site.iplease.irdserver.infra.account.data.dto

import site.iplease.irdserver.infra.account.data.type.AccountType
import site.iplease.irdserver.infra.account.data.type.DepartmentType
import site.iplease.irdserver.infra.account.data.type.PermissionType
import java.net.URI

data class ProfileDto (
    val type: AccountType,
    val permission: PermissionType,
    val accountId: Long,
    val name: String,
    val email: String,
    val profileImage: URI,
    val studentNumber: Int,
    val department: DepartmentType
)