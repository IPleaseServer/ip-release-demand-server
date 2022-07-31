package site.iplease.irdserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IpReleaseDemandServerApplication

fun main(args: Array<String>) {
	runApplication<IpReleaseDemandServerApplication>(*args)
}
