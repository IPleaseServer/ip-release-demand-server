package site.iplease.irdserver.domain.common.strategy

import site.iplease.irdserver.domain.common.data.type.DemandStatusType
import site.iplease.irdserver.domain.common.repository.DemandRepository

class AcceptedDemandRemoveStrategyImpl(
    private val demandRepository: DemandRepository
    ) : AcceptedDemandRemoveStrategy {
    override fun removeAcceptedDemand() {
        demandRepository.deleteAllByStatus(DemandStatusType.ACCEPT).block()
    }
}