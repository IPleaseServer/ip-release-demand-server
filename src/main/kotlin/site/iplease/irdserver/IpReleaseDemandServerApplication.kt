package site.iplease.irdserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling
import site.iplease.irdserver.global.account.config.AccountProperties

@SpringBootApplication
@EnableScheduling
@ConfigurationPropertiesScan(basePackageClasses = [AccountProperties::class])
class IpReleaseDemandServerApplication

fun main(args: Array<String>) {
	runApplication<IpReleaseDemandServerApplication>(*args)
}
