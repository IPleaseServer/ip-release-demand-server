package site.iplease.irdserver.domain.common.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.data.type.DemandPolicyType
import site.iplease.irdserver.domain.common.dto.DemandDto

interface DemandValidator {
    fun validate(dto: DemandDto, demandCreate: DemandPolicyType): Mono<Unit>
}
