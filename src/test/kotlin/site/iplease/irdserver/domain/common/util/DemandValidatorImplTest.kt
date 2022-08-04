package site.iplease.irdserver.domain.common.util

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.common.data.type.DemandPolicyType
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.repository.DemandRepository
import site.iplease.irdserver.infra.assign_ip.data.dto.AssignIpDto
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService
import kotlin.random.Random

class DemandValidatorImplTest {
    private lateinit var demandRepository: DemandRepository
    private lateinit var assignIpQueryService: AssignIpQueryService
    private lateinit var target: DemandValidatorImpl

    @BeforeEach
    fun beforeEach() {
        demandRepository = mock()
        assignIpQueryService = mock()
        target = DemandValidatorImpl(demandRepository, assignIpQueryService)
    }

    //validateDemandCreate 로직
    //같은 할당IP에 대한 해제신청이 존재하는지 검사한다.
    //issuer가 할당IP에 대한 소유권을 가지고있는지 검사한다.
    //할당IP가 존재하는지 검사한다.

    @Test @DisplayName("신청추가정책검증 성공 테스트")
    fun testValidateDemandCreate_success() {
        //given
        val assignIpId = Random.nextLong()
        val assigneeId = Random.nextLong()
        val dto = mock<DemandDto>()
        val assignIp = mock<AssignIpDto>()

        //when
        whenever(dto.assignIpId).thenReturn(assignIpId)
        whenever(dto.issuerId).thenReturn(assigneeId)
        whenever(assignIp.id).thenReturn(assignIpId)
        whenever(assignIp.assigneeId).thenReturn(assigneeId)

        whenever(demandRepository.existsByAssignIpId(assignIpId)).thenReturn(false.toMono())
        whenever(assignIpQueryService.existsById(assignIpId)).thenReturn(true.toMono())
        whenever(assignIpQueryService.findById(assignIpId)).thenReturn(assignIp.toMono())

        val result = target.validate(dto, DemandPolicyType.DEMAND_CREATE).block()!!

        //then
        assertEquals(result, Unit)
        verify(assignIpQueryService, times(1)).existsById(assignIpId)
        verify(assignIpQueryService, times(1)).findById(assignIpId)
    }
}