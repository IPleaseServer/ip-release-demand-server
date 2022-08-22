package site.iplease.irdserver.infra.message.type

enum class MessageType(
    val routingKey: String
) {
    SEND_ALARM("sendAlarm"),
    ASSIGN_IP_CREATE("assignIpCreate"),
    UNKNOWN(""),;

    companion object {
        fun of(routingKey: String) =
            kotlin.runCatching { values().first { it.routingKey == routingKey } }
                .getOrElse { UNKNOWN }
    }
}