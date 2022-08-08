package site.iplease.irdserver.domain.demand.strategy

import org.springframework.stereotype.Component
import site.iplease.irdserver.domain.demand.data.type.DemandStatusType
import site.iplease.irdserver.domain.demand.repository.DemandRepository

@Component
class AcceptedDemandRemoveStrategyImpl(
    private val demandRepository: DemandRepository
    ) : AcceptedDemandRemoveStrategy {
    override fun removeAcceptedDemand() {
        demandRepository.deleteAllByStatus(DemandStatusType.ACCEPT).block()
    }
}