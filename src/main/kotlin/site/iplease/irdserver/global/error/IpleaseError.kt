package site.iplease.irdserver.global.error

interface IpleaseError {
    fun getErrorMessage(): String
    fun getErrorDetail(): String
}