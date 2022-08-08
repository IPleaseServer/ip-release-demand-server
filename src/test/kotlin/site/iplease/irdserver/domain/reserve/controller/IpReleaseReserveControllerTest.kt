package site.iplease.irdserver.domain.reserve.controller

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
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
}