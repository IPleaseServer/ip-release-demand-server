package site.iplease.irdserver.domain.common.util

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
import site.iplease.irdserver.domain.common.repository.DemandRepository
import site.iplease.irdserver.infra.assign_ip.service.AssignIpQueryService

class DemandValidatorImplTest {
    private lateinit var demandRepository: DemandRepository
    private lateinit var demandConverter: DemandConverter
    private lateinit var assignIpQueryService: AssignIpQueryService
    private lateinit var target: DemandValidatorImpl

    @BeforeEach
    fun beforeEach() {
        demandRepository = mock()
        demandConverter = mock()
        assignIpQueryService = mock()
        target = DemandValidatorImpl(demandRepository, demandConverter, assignIpQueryService)
    }
}