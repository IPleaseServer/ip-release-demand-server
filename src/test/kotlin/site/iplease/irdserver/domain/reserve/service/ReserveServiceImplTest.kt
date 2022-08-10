package site.iplease.irdserver.domain.reserve.service

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
import site.iplease.irdserver.domain.reserve.data.entity.Reserve
import site.iplease.irdserver.domain.reserve.repository.ReserveRepository
import site.iplease.irdserver.domain.reserve.util.ReserveConverter
import site.iplease.irdserver.domain.reserve.util.ReserveValidator

class ReserveServiceImplTest {
    private lateinit var reserveConverter: ReserveConverter
    private lateinit var reserveRepository: ReserveRepository
    private lateinit var reserveValidator: ReserveValidator
    private lateinit var target: ReserveServiceImpl

    @BeforeEach
    fun beforeEach() {
        reserveConverter = mock()
        reserveRepository = mock()
        reserveValidator = mock()
        target = ReserveServiceImpl(reserveConverter, reserveRepository)
    }

    @Test @DisplayName("IP할당해제예약추가 성공 테스트")
    fun testAddDemand_success() {
        //given
        val dto = mock<ReserveDto>()
        val entity = mock<Reserve>()
        val newEntity = mock<Reserve>()
        val savedEntity = mock<Reserve>()
        val expectedResult = mock<ReserveDto>()

        //when
        whenever(reserveConverter.toEntity(dto)).thenReturn(entity.toMono())
        whenever(entity.copy(id=0)).thenReturn(newEntity)
        whenever(reserveRepository.save(newEntity)).thenReturn(savedEntity.toMono())
        whenever(reserveConverter.toDto(savedEntity)).thenReturn(expectedResult.toMono())

        val result = target.addReserve(dto).block()!!

        //then
        assertEquals(expectedResult, result)
        verify(reserveRepository, times(1)).save(newEntity)
    }
}