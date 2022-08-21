package site.iplease.irdserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class IpReleaseDemandServerApplication

fun main(args: Array<String>) {
	runApplication<IpReleaseDemandServerApplication>(*args)
}
