package site.iplease.irdserver.domain.common.util

import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.data.type.DemandPolicyType
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.repository.DemandRepository
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService

class DemandValidatorImpl(
    private val demandRepository: DemandRepository,
    private val demandConverter: DemandConverter,
    private val assignIpQueryService: AssignIpQueryService
): DemandValidator {
    override fun validate(dto: DemandDto, demandCreate: DemandPolicyType): Mono<Unit> {
        TODO("Not yet implemented")
    }
}