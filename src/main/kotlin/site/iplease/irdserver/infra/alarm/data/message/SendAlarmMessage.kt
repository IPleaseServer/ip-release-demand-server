package site.iplease.irdserver.infra.alarm.data.message

import site.iplease.irdserver.infra.alarm.data.type.AlarmType

data class SendAlarmMessage (
    val type: AlarmType,
    val receiverId: Long,
    val title: String,
    val description: String
)