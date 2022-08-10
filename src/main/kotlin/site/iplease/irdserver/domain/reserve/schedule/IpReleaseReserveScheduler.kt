package site.iplease.irdserver.domain.reserve.schedule

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import site.iplease.irdserver.domain.reserve.strategy.IpReleaseReserveStrategy

@Component
class IpReleaseReserveScheduler(
    private val ipReleaseReserveStrategy: IpReleaseReserveStrategy
) {
    //scheduled every day 6:00 am
    @Scheduled(cron = "0 0 6 * * *")
    fun addDemandAndRemoveReserve() {
        ipReleaseReserveStrategy.addDemandAndRemoveReserve()
    }
}