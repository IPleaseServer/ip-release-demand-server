package site.iplease.irdserver.global.account.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties("iplease.account")
data class AccountProperties(val adminId: Long)