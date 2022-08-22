package site.iplease.irdserver.domain.accept.subscriber

import org.springframework.stereotype.Component
import site.iplease.irdserver.domain.reserve.data.dto.ReserveDto
import site.iplease.irdserver.global.accept.data.message.AssignIpCreateMessage
import site.iplease.irdserver.global.accept.subscriber.AssignIpCreateSubscriber
import site.iplease.irdserver.global.account.config.AccountProperties
import site.iplease.irdserver.global.reserve.service.ReserveService

@Component
class AssignIpCreateSubscriberImpl(
    private val reserveService: ReserveService,
    private val accountProperties: AccountProperties
): AssignIpCreateSubscriber {
    override fun subscribe(message: AssignIpCreateMessage) {
        reserveService.addReserve(ReserveDto(0, message.id, accountProperties.adminId, message.expireAt)).block()!!
    }
}