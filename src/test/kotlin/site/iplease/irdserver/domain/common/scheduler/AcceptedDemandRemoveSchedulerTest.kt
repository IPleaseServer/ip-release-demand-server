package site.iplease.irdserver.domain.common.scheduler

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import site.iplease.irdserver.domain.common.strategy.AcceptedDemandRemoveStrategy

class AcceptedDemandRemoveSchedulerTest {
    private lateinit var strategy: AcceptedDemandRemoveStrategy
    private lateinit var target: AcceptedDemandRemoveScheduler

    @BeforeEach
    fun beforeEach() {
        strategy = mock()
        target = AcceptedDemandRemoveScheduler(strategy)
    }

    @Test @DisplayName("수락된IP할당해제신청제거 성공 테스트")
    fun testRemoveAcceptedDemand_success() {
        //when
        target.removeAcceptedDemand()

        //then
        verify(strategy, times(1)).removeAcceptedDemand()
    }
}