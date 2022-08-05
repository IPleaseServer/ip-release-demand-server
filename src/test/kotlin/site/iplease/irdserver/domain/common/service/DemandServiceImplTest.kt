package site.iplease.irdserver.domain.common.service

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.common.data.entity.Demand
import site.iplease.irdserver.domain.common.data.type.DemandPolicyType
import site.iplease.irdserver.domain.common.dto.DemandDto
import site.iplease.irdserver.domain.common.repository.DemandRepository
import site.iplease.irdserver.domain.common.util.DemandConverter
import site.iplease.irdserver.domain.common.util.DemandValidator

class DemandServiceImplTest {
    private lateinit var demandRepository: DemandRepository
    private lateinit var demandValidator: DemandValidator
    private lateinit var demandConverter: DemandConverter
    private lateinit var demandService: DemandServiceImpl

    @BeforeEach
    fun beforeEach() {
        demandRepository = mock()
        demandValidator = mock()
        demandConverter = mock()
        demandService = DemandServiceImpl(demandRepository, demandValidator, demandConverter)
    }

    @Test @DisplayName("신청추가 성공 테스트")
    fun addDemand_success() {
        //given
        val dto = mock<DemandDto>()
        val entity = mock<Demand>()
        val newEntity = mock<Demand>()
        val savedEntity = mock<Demand>()
        val expectedResult = mock<DemandDto>()

        //when
        whenever(demandValidator.validate(dto, DemandPolicyType.DEMAND_CREATE)).thenReturn(Unit.toMono())

        whenever(demandConverter.toEntity(dto)).thenReturn(entity.toMono())
        whenever(entity.copy(id=0)).thenReturn(newEntity)
        whenever(demandRepository.save(newEntity)).thenReturn(savedEntity.toMono())
        whenever(demandConverter.toDto(savedEntity)).thenReturn(expectedResult.toMono())

        val result = demandService.addDemand(dto).block()!!

        //then
        assertEquals(result, expectedResult)
        verify(demandValidator, times(1)).validate(dto, DemandPolicyType.DEMAND_CREATE)
        verify(demandRepository, times(1)).save(newEntity)
    }
}