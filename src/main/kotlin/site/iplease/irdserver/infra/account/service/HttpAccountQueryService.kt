package site.iplease.irdserver.infra.account.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.infra.account.data.dto.ProfileDto
import site.iplease.irdserver.infra.account.data.response.ProfileResponse
import site.iplease.irdserver.infra.account.util.AccountConverter
import site.iplease.irdserver.infra.assign_ip.data.type.QueryType
import site.iplease.irdserver.infra.assign_ip.exception.ApiException

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
            .retrieve()
            .onStatus({ !it.is2xxSuccessful }, { handleError(QueryType.QUERY_ACCOUNT_BY_ID, it) })
            .bodyToMono(ProfileResponse::class.java)
            .flatMap { accountConverter.toDto(it) }

    fun handleError(type: QueryType, response: ClientResponse): Mono<Throwable> =
        response.bodyToMono(ErrorResponse::class.java)
            .flatMap { error -> Mono.error(ApiException(type = type, response = error)) }
}