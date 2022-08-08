package site.iplease.irdserver.infra.account.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import site.iplease.irdserver.infra.account.data.dto.ProfileDto
import site.iplease.irdserver.infra.account.data.response.ProfileResponse
import site.iplease.irdserver.infra.account.util.AccountConverter

@Service
class HttpAccountQueryService(
    private val webClientBuilder: WebClient.Builder,
    private val accountConverter: AccountConverter
): AccountQueryService {
    override fun findById(accountId: Long): Mono<ProfileDto> =
        webClientBuilder
            .build()
            .get()
            .uri("lb://account-server/api/v1/account/profile/query/id/$accountId")
            .retrieve().bodyToMono(ProfileResponse::class.java)
            .flatMap { accountConverter.toDto(it) }
}