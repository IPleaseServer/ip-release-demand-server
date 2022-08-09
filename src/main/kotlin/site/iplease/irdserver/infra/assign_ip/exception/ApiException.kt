package site.iplease.irdserver.infra.assign_ip.exception

import site.iplease.irdserver.global.error.ErrorResponse
import site.iplease.irdserver.global.error.IpleaseError
import site.iplease.irdserver.infra.assign_ip.data.type.QueryType

data class ApiException(val type: QueryType, val response: ErrorResponse)
    : RuntimeException("HTTP 요청중 오류가 발생하였습니다! 요청 종류: $type 응답 정보: $response"), IpleaseError {
    companion object { const val errorMessage = "API 요청중 오류가 발생하였습니다!" }
    override fun getErrorMessage(): String = "$errorMessage - ${response.message}"

    override fun getErrorDetail() = response.detail

}