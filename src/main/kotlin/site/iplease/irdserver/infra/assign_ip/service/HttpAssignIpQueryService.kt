package site.iplease.irdserver.infra.assign_ip.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.infra.assign_ip.data.dto.AssignIpDto
import site.iplease.irdserver.infra.assign_ip.data.response.AssignIpQueryResponse

@Service
class HttpAssignIpQueryService(
    private val webClientBuilder: WebClient.Builder
    ): AssignIpQueryService {
    //TODO 한번 API 호출을 한 이후, 해당 AssignIp에 대한 정보를 캐싱하도록 로직 수정하기
    override fun existsById(assignIpId: Long): Mono<Boolean> =
        webClientBuilder
            .build()
            .get()
            .uri("lb://assign-ip-manage-server/api/v1/assign-ip/query/$assignIpId")
            .retrieve().toEntity(AssignIpQueryResponse::class.java)
            .flatMap { entity ->
                if(entity.body != null) true.toMono()
                else false.toMono()
            }

    override fun findById(assignIpId: Long): Mono<AssignIpDto> =
        webClientBuilder
            .build()
            .get()
            .uri("lb://assign-ip-manage-server/api/v1/assign-ip/query/$assignIpId")
            .retrieve().bodyToMono(AssignIpQueryResponse::class.java)
            .map { it.data }
}