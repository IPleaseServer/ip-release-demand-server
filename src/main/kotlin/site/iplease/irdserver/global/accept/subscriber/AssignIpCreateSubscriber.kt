package site.iplease.irdserver.global.accept.subscriber

import site.iplease.irdserver.global.accept.data.message.AssignIpCreateMessage

interface AssignIpCreateSubscriber {
    fun subscribe(message: AssignIpCreateMessage)
}
