package site.iplease.irdserver.infra.account.service

import reactor.core.publisher.Mono
import site.iplease.irdserver.infra.account.data.dto.ProfileDto

interface AccountQueryService {
    fun findById(accountId: Long): Mono<ProfileDto>
}