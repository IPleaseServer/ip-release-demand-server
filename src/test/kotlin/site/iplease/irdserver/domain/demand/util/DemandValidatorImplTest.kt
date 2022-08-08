package site.iplease.irdserver.domain.demand.util

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.demand.data.entity.Demand
import site.iplease.irdserver.domain.demand.data.type.DemandPolicyType
import site.iplease.irdserver.domain.demand.data.type.DemandStatusType
import site.iplease.irdserver.domain.demand.dto.DemandDto
import site.iplease.irdserver.domain.demand.repository.DemandRepository
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

    //validateDemandAccept 로직
    //아래의 조건을 검사한다.
    //- 신청이 존재해야한다.
    //- 신청의 상태가 ACCEPT가 아니어야한다.
    @Test @DisplayName("신청수락정책검증 성공 테스트")
    fun testValidateDemandAccept_success() {
        //given
        val demandId = Random.nextLong()
        val status = DemandStatusType.values().filter { it != DemandStatusType.ACCEPT }.random() //TODO 나중에 canAccept(): Boolean 메서드로 필터 대체
        val dto = mock<DemandDto>()
        val entity = mock<Demand>()

        //when
        whenever(dto.id).thenReturn(demandId)
        whenever(entity.status).thenReturn(status)
        whenever(demandRepository.existsById(demandId)).thenReturn(true.toMono())
        whenever(demandRepository.findById(demandId)).thenReturn(entity.toMono())

        val result = target.validate(dto, DemandPolicyType.DEMAND_ACCEPT).block()!!

        //then
        assertEquals(result, Unit)
        verify(demandRepository, times(1)).existsById(demandId)
        verify(demandRepository, times(1)).findById(demandId)
    }

    //validateDemandCancel 로직
    //아래의 조건을 검사한다.
    //- 신청이 존재해야한다.
    //- issuer가 신청에 대한 소유권을 가지고있어야한다.

    @Test @DisplayName("신청취소정책검증 성공 테스트")
    fun testValidateDemandCancel_success() {
        //given
        val issuerId = Random.nextLong()
        val demandId = Random.nextLong()
        val dto = mock<DemandDto>()
        val entity = mock<Demand>()

        //when
        whenever(dto.id).thenReturn(demandId)
        whenever(dto.issuerId).thenReturn(issuerId)
        whenever(entity.issuerId).thenReturn(issuerId)
        whenever(demandRepository.existsById(dto.id)).thenReturn(true.toMono())
        whenever(demandRepository.findById(demandId)).thenReturn(entity.toMono())

        val result = target.validate(dto, DemandPolicyType.DEMAND_CANCEL).block()!!

        //then
        assertEquals(result, Unit)
        verify(demandRepository, times(1)).existsById(dto.id)
        verify(demandRepository, times(1)).findById(dto.id)
    }

    //validateDemandCreate 로직
    //아래의 조건을 검사한다.
    //- 같은 할당IP에 대한 해제신청이 존재하지 않아야한다.
    //- issuer가 할당IP에 대한 소유권을 가지고있어야한다.
    //- 할당IP가 존재해야한다.

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