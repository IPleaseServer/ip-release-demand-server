package site.iplease.irdserver.infra.assign_ip.exception

import org.springframework.http.ResponseEntity
import site.iplease.irdserver.infra.assign_ip.data.type.HttpRequestType

class HttpRequestException(type: HttpRequestType, response: ResponseEntity<*>): RuntimeException("HTTP 요청중 오류가 발생하였습니다! 요청 종류: $type 응답 정보: $response")