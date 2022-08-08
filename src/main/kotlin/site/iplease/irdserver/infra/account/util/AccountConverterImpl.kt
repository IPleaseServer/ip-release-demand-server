package site.iplease.irdserver.infra.account.util

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.infra.account.data.dto.ProfileDto
import site.iplease.irdserver.infra.account.data.response.ProfileResponse
import site.iplease.irdserver.infra.account.data.type.DepartmentType
import java.net.URI

@Service
class AccountConverterImpl: AccountConverter{
    override fun toDto(response: ProfileResponse): Mono<ProfileDto> =
        Unit.toMono().map { ProfileDto(
            type = response.type,
            permission = response.common.permission,
            accountId = response.common.accountId,
            name = response.common.name,
            email = response.common.email,
            profileImage = URI.create(response.common.profileImage),
            studentNumber = response.student?.studentNumber ?: 0,
            department = response.student?.department ?: DepartmentType.SOFTWARE_DEVELOP,
        ) }
}