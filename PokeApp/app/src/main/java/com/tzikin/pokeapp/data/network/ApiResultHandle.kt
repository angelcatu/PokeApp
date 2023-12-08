package com.tzikin.pokeapp.data.network

sealed class ApiResultHandle<out T>(
    val data: T? = null,
    val message: ErrorResponse? = null,
    val code: String? = null
) {
    data class Success<out T>(val value: T) : ApiResultHandle<T>(data = value)
    data class ApiError(val httpCode: String? = null, val error: ErrorResponse) :
        ApiResultHandle<Nothing>(code = httpCode, message = error)
    data class NetworkError(val errorNetworkMessage: ErrorResponse) :
        ApiResultHandle<Nothing>(message = errorNetworkMessage)
}