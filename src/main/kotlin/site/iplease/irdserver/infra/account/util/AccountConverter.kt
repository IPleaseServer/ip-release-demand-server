package site.iplease.irdserver.infra.account.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.infra.account.data.dto.ProfileDto
import site.iplease.irdserver.infra.account.data.response.ProfileResponse

interface AccountConverter {
    fun toDto(response: ProfileResponse): Mono<ProfileDto>
}
