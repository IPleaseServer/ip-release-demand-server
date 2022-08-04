package site.iplease.irdserver.domain.common.service

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
import site.iplease.irdserver.domain.common.repository.DemandRepository
import site.iplease.irdserver.domain.common.util.DemandValidator

class DemandServiceImplTest {
    private lateinit var demandRepository: DemandRepository
    private lateinit var demandValidator: DemandValidator
    private lateinit var demandService: DemandServiceImpl

    @BeforeEach
    fun beforeEach() {
        demandRepository = mock()
        demandService = DemandServiceImpl(demandRepository, demandValidator)
    }
}