package site.iplease.irdserver.domain.demand.strategy

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import reactor.kotlin.core.publisher.toMono
import site.iplease.irdserver.domain.demand.data.type.DemandStatusType
import site.iplease.irdserver.domain.demand.repository.DemandRepository

class AcceptedDemandRemoveStrategyImplTest {
    private lateinit var demandRepository: DemandRepository
    private lateinit var target: AcceptedDemandRemoveStrategyImpl

    @BeforeEach
    fun beforeEach() {
        demandRepository = mock()
        target = AcceptedDemandRemoveStrategyImpl(demandRepository)
    }

    @Test @DisplayName("수락된IP할당해제신청제거 성공 테스트")
    fun testRemoveAcceptedDemand_success() {
        //when
        whenever(demandRepository.deleteAllByStatus(DemandStatusType.ACCEPT)).thenReturn(Unit.toMono().then())

        target.removeAcceptedDemand()

        //then
        verify(demandRepository, times(1)).deleteAllByStatus(DemandStatusType.ACCEPT)
    }
}