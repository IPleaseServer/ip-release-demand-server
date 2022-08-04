package site.iplease.irdserver.domain.common.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import site.iplease.irdserver.domain.common.data.request.CreateReleaseDemandRequest
import site.iplease.irdserver.domain.common.data.response.CreateReleaseDemandResponse

@RestController
@RequestMapping("/api/v1/demand/release")
class IpReleaseDemandController {
    @PostMapping
    fun createReleaseDemand(@RequestBody releaseDemand: CreateReleaseDemandRequest): Mono<ResponseEntity<CreateReleaseDemandResponse>> {
        TODO()
    }
}