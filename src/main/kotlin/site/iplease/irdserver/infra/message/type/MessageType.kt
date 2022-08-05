package site.iplease.irdserver.infra.message.type

enum class MessageType(
    val routingKey: String
) {
    SEND_ALARM("sendAlarm"),
    UNKNOWN(""),;

    companion object {
        fun of(routingKey: String) =
            kotlin.runCatching { values().first { it.routingKey == routingKey } }
                .getOrElse { UNKNOWN }
    }
}