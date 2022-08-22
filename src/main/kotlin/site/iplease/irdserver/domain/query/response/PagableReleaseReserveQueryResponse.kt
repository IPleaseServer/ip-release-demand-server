package site.iplease.irdserver.domain.query.response

import org.springframework.data.domain.Page
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto

data class PagableReleaseReserveQueryResponse(
    val data: Page<ReserveDto>
)
