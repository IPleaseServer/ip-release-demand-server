package site.iplease.irdserver.domain.reserve.controller

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.TestDummyDataUtil
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.domain.reserve.data.request.CreateIpReleaseReserveRequest
import site.iplease.irdserver.domain.reserve.data.response.CreateIpReleaseReserveResponse
import site.iplease.irdserver.domain.reserve.service.ReserveService
import site.iplease.irdserver.domain.reserve.util.ReserveConverter

class IpReleaseReserveControllerTest {
    private lateinit var reserveService: ReserveService
    private lateinit var reserveConverter: ReserveConverter
    private lateinit var target: IpReleaseReserveController

    @BeforeEach
    fun beforeEach() {
        reserveService = mock()
        reserveConverter = mock()
        target = IpReleaseReserveController(reserveService, reserveConverter)
    }

    @Test @DisplayName("IP할당해제예약추가 성공 테스트")
    fun createIpReleaseReserve_success() {
        //given
        val assignIpId = TestDummyDataUtil.id()
        val issuerId = TestDummyDataUtil.id()
        val releaseAt = TestDummyDataUtil.randomDate()
        val request = CreateIpReleaseReserveRequest(assignIpId, releaseAt)
        val dto = mock<ReserveDto>()
        val createdReserve = mock<ReserveDto>()
        val response = mock<CreateIpReleaseReserveResponse>()

        //when
        whenever(reserveConverter.toDto(request, issuerId)).thenReturn(dto.toMono())
        whenever(reserveService.addReserve(dto)).thenReturn(createdReserve.toMono())
        whenever(reserveConverter.toCreateIpReleaseReserveResponse(createdReserve)).thenReturn(response.toMono())

        val result = target.createIpReleaseReserve(issuerId = issuerId, request = request).block()!!

        //then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertEquals(result.body, response)
        verify(reserveService, times(1)).addReserve(dto)
    }
}