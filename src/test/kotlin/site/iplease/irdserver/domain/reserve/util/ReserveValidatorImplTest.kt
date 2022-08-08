package site.iplease.irdserver.domain.reserve.util

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.type.ReservePermission
import site.iplease.irdserver.domain.reserve.data.type.ReservePolicyType
import site.iplease.irdserver.domain.reserve.repository.ReserveRepository
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService
import kotlin.random.Random

class ReserveValidatorImplTest {
    private lateinit var reserveRepository: ReserveRepository
    private lateinit var reservePermissionValidator: ReservePermissionValidator
    private lateinit var assignIpQueryService: AssignIpQueryService
    private lateinit var target: ReserveValidatorImpl

    @BeforeEach
    fun beforeEach() {
        reserveRepository = mock()
        reservePermissionValidator = mock()
        assignIpQueryService = mock()
        target = ReserveValidatorImpl(reserveRepository, reservePermissionValidator, assignIpQueryService)
    }

    //IP할당해제예약추가정책은 다음과 같다
    //- 동일한 할당IP에 대한 할당해제예약이 존재하면 안된다.
    //- 할당IP가 존재해야한다.
    //- issuer가 해당 할당IP에 대한 해제예약추가권한을 소유해야한다.
    @Test @DisplayName("IP할당해제예약추가정책검증 성공 테스트")
    fun testValidateCreate_success() {
        //given
        val assignIpId = Random.nextLong()
        val issuerId = Random.nextLong()
        val dto = mock<ReserveDto>()

        //when
        whenever(dto.assignIpId).thenReturn(assignIpId)
        whenever(dto.issuerId).thenReturn(issuerId)

        whenever(reserveRepository.existsByAssignIpId(assignIpId)).thenReturn(false.toMono())
        whenever(assignIpQueryService.existsById(assignIpId)).thenReturn(true.toMono())
        whenever(reservePermissionValidator.validate(ReservePermission.CREATE, assignIpId = assignIpId, issuerId = issuerId)).thenReturn(Unit.toMono())

        val result = target.validate(dto, ReservePolicyType.RESERVE_CREATE).block()!!

        //then
        assertEquals(result, Unit)
        verify(reserveRepository, times(1)).existsByAssignIpId(assignIpId)
        verify(assignIpQueryService, times(1)).existsById(assignIpId)
        verify(reservePermissionValidator, times(1)).validate(ReservePermission.CREATE, assignIpId = assignIpId, issuerId = issuerId)
    }
}