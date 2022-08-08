package site.iplease.irdserver.infra.assign_ip.service

import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.toEntity
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.infra.assign_ip.data.response.RemoveAssignIpResponse
import site.iplease.irdserver.infra.assign_ip.data.type.HttpRequestType
import site.iplease.irdserver.infra.assign_ip.exception.HttpRequestException

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
            .toEntity<RemoveAssignIpResponse>()
            .flatMap {
                if(!it.statusCode.is2xxSuccessful) Mono.error(HttpRequestException(HttpRequestType.REMOVE_ASSIGN_IP, it))
                else Unit.toMono()
            }
}