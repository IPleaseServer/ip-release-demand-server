package site.iplease.irdserver.infra.assign_ip.service

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.global.error.ErrorStatus
import site.iplease.irdserver.infra.assign_ip.data.dto.AssignIpDto
import site.iplease.irdserver.infra.assign_ip.data.response.AssignIpQueryResponse
import site.iplease.irdserver.infra.assign_ip.data.response.ExistsAssignIpQueryResponse
import site.iplease.irdserver.infra.assign_ip.data.type.QueryType
import site.iplease.irdserver.infra.assign_ip.exception.ApiException

@Service
class HttpAssignIpQueryService(
    private val webClientBuilder: WebClient.Builder
    ): AssignIpQueryService {
    //TODO 한번 API 호출을 한 이후, 해당 AssignIp에 대한 정보를 캐싱하도록 로직 수정하기
    override fun existsById(assignIpId: Long): Mono<Boolean> =
        webClientBuilder
            .build()
            .get()
            .uri("lb://assign-ip-manage-server/api/v1/assign-ip/query/$assignIpId/exists")
            .retrieve()
            .onStatus({ !it.is2xxSuccessful }, { handleError(QueryType.QUERY_EXISTS_ASSIGN_IP_BY_ID, it) })
            .bodyToMono(ExistsAssignIpQueryResponse::class.java)
            .map { response -> response.isExists }

    override fun findById(assignIpId: Long): Mono<AssignIpDto> =
        webClientBuilder
            .build()
            .get()
            .uri("lb://assign-ip-manage-server/api/v1/assign-ip/query/$assignIpId")
            .retrieve()
            .onStatus({ !it.is2xxSuccessful }, { handleError(QueryType.QUERY_ASSIGN_IP_BY_ID, it) })
            .bodyToMono(AssignIpQueryResponse::class.java)
            .map { it.data }

    fun handleError(type: QueryType, response: ClientResponse): Mono<Throwable> =
        response.toMono()
            .flatMap { it.bodyToMono(ErrorResponse::class.java) }
            .flatMap<Throwable?> { error -> Mono.error(ApiException(type = type, response = error)) }
            .onErrorReturn(ApiException(type = type, response = ErrorResponse(
                status = ErrorStatus.API_ERROR,
                message = "서버에서 오류가 발생했습니다.",
                detail = "서버에서 외부 API통신중 알 수 없는 오류가 발생하였습니다! 요청: $type 응답상태: ${response.statusCode()}"
            )))
}