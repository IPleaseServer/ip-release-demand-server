package site.iplease.irdserver.domain.query.response

import org.springframework.data.domain.Page
import site.iplease.irdserver.domain.demand.dto.DemandDto

data class PagableReleaseDemandQueryResponse(
    val data: Page<DemandDto>
)