package site.iplease.irdserver.domain.common.controller

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.common.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.domain.common.data.response.AcceptReleaseDemandResponse
import site.iplease.irdserver.domain.common.data.response.CancelReleaseDemandResponse
import site.iplease.irdserver.domain.common.data.response.CreateReleaseDemandResponse
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.service.DemandService
import site.iplease.irdserver.domain.common.util.DemandConverter
import kotlin.random.Random

class IpReleaseDemandControllerTest {
    private lateinit var demandConverter: DemandConverter
    private lateinit var demandService: DemandService
    private lateinit var target: IpReleaseDemandController
    //private lateinit var

    @BeforeEach
    fun beforeEach() {
        demandConverter = mock()
        demandService = mock()
        target = IpReleaseDemandController(demandConverter, demandService)
    }

    //acceptReleaseDemand 로직
    //요청정보를 DemandDto로 치환한다. DemandConverter
    //치환된 DemandDto를 통해 신청수락 트랜잭션을 호출한다. DemandService
    //수락된 신청의 Id를 통해 응답값을 구성한다.
    //구성된 응답값을 ResponseEntity에 감싸서 반환한다.

    @Test @DisplayName("할당해제신청수락 성공 테스트")
    fun testAcceptReleaseDemand_success() {
        //given
        val demandId = Random.nextLong()
        val acceptDemandId = Random.nextLong().let { if (it == demandId) 1-demandId else it } //중복 및 오버플로우 방지
        val dto = mock<DemandDto>()
        val response = mock<AcceptReleaseDemandResponse>()

        //when
        whenever(demandConverter.toDto(demandId)).thenReturn(dto.toMono())
        whenever(demandService.acceptDemand(dto)).thenReturn(acceptDemandId.toMono())
        whenever(demandConverter.toAcceptReleaseDemandResponse(acceptDemandId)).thenReturn(response.toMono())

        val result = target.acceptReleaseDemand(demandId).block()!!

        //then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertEquals(result.body, response)
    }

    //cancelReleaseDemand 로직
    //요청정보를 DemandDto로 치환한다. DemandConverter
    //치환된 DemandDto를 통해 신청취소 트랜잭션을 호출한다. DemandService
    //취소된 신청의 Id를 통해 응답값을 구성한다.
    //구성된 응답값을 ResponseEntity에 감싸서 반환한다.

    @Test @DisplayName("할당해제신청취소 성공 테스트")
    fun testCancelReleaseDemand_success() {
        //given
        val issuerId = Random.nextLong()
        val demandId = Random.nextLong()
        val removedDemandId = Random.nextLong().let { if (it == demandId) 1-demandId else it } //중복 및 오버플로우 방지
        val dto = mock<DemandDto>()
        val response = mock<CancelReleaseDemandResponse>()

        //when
        whenever(demandConverter.toDto(issuerId, demandId)).thenReturn(dto.toMono())
        whenever(demandService.cancelDemand(dto)).thenReturn(removedDemandId.toMono())
        whenever(demandConverter.toCancelReleaseDemandResponse(removedDemandId)).thenReturn(response.toMono())

        val result = target.cancelReleaseDemand(issuerId, demandId).block()!!

        //then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertEquals(result.body, response)
    }

   //createReleaseDemand 로직
    //요청정보를 DemandDto로 치환한다. DemandConverter
    //치환된 DemandDto를 통해 신청추가 트랜잭션을 호출한다. DemandService
    //추가된 Dto를 통해 응답값을 구성한다. DemandConverter
    //구성된 응답값을 ResponseEntity에 감싸서 반환한다.

    @Test @DisplayName("할당해제신청추가 성공 테스트")
    fun testCreateReleaseDemand_success() {
        //given
        val request = mock<CreateReleaseDemandRequest>()
        val dto = mock<DemandDto>()
        val resultDto = mock<DemandDto>()
        val response = mock<CreateReleaseDemandResponse>()

        //when
        whenever(demandConverter.toDto(request)).thenReturn(dto.toMono())
        whenever(demandConverter.toCreateReleaseDemandResponse(resultDto)).thenReturn(response.toMono())
        whenever(demandService.addDemand(dto)).thenReturn(resultDto.toMono())

        val result = target.createReleaseDemand(request).block()!!

        //then
        assertTrue(result.statusCode.is2xxSuccessful)
        assertEquals(result.body, response)
    }
}