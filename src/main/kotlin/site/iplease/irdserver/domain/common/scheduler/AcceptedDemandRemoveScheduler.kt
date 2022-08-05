package site.iplease.irdserver.domain.common.scheduler

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import site.iplease.irdserver.domain.common.strategy.AcceptedDemandRemoveStrategy

@Component
class AcceptedDemandRemoveScheduler(private val strategy: AcceptedDemandRemoveStrategy) {
    @Scheduled(cron = "0 0 3 * * *") //매일 오전3시에 실행
    fun removeAcceptedDemand() = strategy.removeAcceptedDemand()
}
