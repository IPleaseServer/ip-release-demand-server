package site.iplease.irdserver.infra.assign_ip.service

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.infra.assign_ip.data.response.RemoveAssignIpResponse
import site.iplease.irdserver.infra.assign_ip.data.type.QueryType
import site.iplease.irdserver.infra.assign_ip.exception.ApiException

@Component
class HttpAssignIpCommandService(
    private val webClientBuilder: WebClient.Builder
): AssignIpCommandService {
    override fun removeAssignIpById(assignIpId: Long): Mono<Unit> =
        webClientBuilder
            .build()
            .delete()
            .uri("lb://assign-ip-manage-server/api/v1/assign-ip/command/$assignIpId")
            .retrieve()
            .onStatus({ !it.is2xxSuccessful }, { handleError(QueryType.REMOVE_ASSIGN_IP, it) })
            .bodyToMono(RemoveAssignIpResponse::class.java)
            .map {  }

    fun handleError(type: QueryType, response: ClientResponse): Mono<Throwable> =
        response.bodyToMono(ErrorResponse::class.java)
            .flatMap { error -> Mono.error(ApiException(type = type, response = error)) }
}