package site.iplease.irdserver.infra.message.service

import reactor.core.publisher.Mono
import site.iplease.irdserver.infra.message.type.MessageType

interface MessagePublishService {
    fun publish(type: MessageType, data: Any): Mono<Unit>
}
