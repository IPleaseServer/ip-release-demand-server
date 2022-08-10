package site.iplease.irdserver.domain.reserve.strategy

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.global.demand.data.response.CreateReleaseDemandResponse
import site.iplease.irdserver.domain.reserve.repository.ReserveRepository
import site.iplease.irdserver.global.common.util.DateUtil
import site.iplease.irdserver.global.demand.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.infra.assign_ip.data.type.QueryType
import site.iplease.irdserver.infra.assign_ip.exception.ApiException
import kotlin.random.Random

@Component
class IpReleaseReserveStrategyImpl(
    private val reserveRepository: ReserveRepository,
    private val dateUtil: DateUtil,
    private val webClientBuilder: WebClient.Builder //TODO infra로 넘길지 고민하기
): IpReleaseReserveStrategy {
    private val logger = LoggerFactory.getLogger(this::class.java)

    override fun addDemandAndRemoveReserve() {
        logger.info("IP할당해제예약에 대한 일괄 처리 작업(만료된 예약을 토대로 신청 구성)을 시작합니다!")
        reserveRepository.findAllByReleaseAt(dateUtil.dateNow())
            .flatMap { reserve ->
                webClientBuilder.build()
                    .post()
                    .uri("lb://ip-release-demand-server/api/v1/release/demand")
                    .header("X-Authorization-Id", "${reserve.issuerId}")
                    .bodyValue(CreateReleaseDemandRequest(reserve.assignIpId))
                    .retrieve()
                    .onStatus({ !it.is2xxSuccessful }, { handleError(QueryType.ADD_RELEASE_DEMAND, it) })
                    .bodyToMono(CreateReleaseDemandResponse::class.java)
                    .map { reserve }
            }.flatMap { reserve -> reserveRepository.deleteById(reserve.id).then(reserve.toMono()) }
            .map {
                val id = Random.nextLong()
                logger.info("[$id] id가 ${it.id}인 예약을 신청으로 변경하였습니다!")
            }.onErrorContinue{ e, _ ->
                val id = Random.nextLong()
                logger.warn("[$id] 예약을 신청으로 변경하는 도중 오류가 발생하였습니다!")
                logger.warn("[$id] message: ${e.message}")
                logger.trace("[$id] stack trace\n ${e.stackTrace}")
            }.collectList().block()!!
    }

    fun handleError(type: QueryType, response: ClientResponse): Mono<Throwable> =
        response.bodyToMono(ErrorResponse::class.java)
            .flatMap { error -> Mono.error(ApiException(type = type, response = error)) }
}