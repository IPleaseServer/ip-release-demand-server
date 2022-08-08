package site.iplease.irdserver.domain.demand.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.demand.data.type.DemandPolicyType
import site.iplease.irdserver.domain.demand.dto.DemandDto

interface DemandValidator {
    fun validate(dto: DemandDto, policyType: DemandPolicyType): Mono<Unit>
}
