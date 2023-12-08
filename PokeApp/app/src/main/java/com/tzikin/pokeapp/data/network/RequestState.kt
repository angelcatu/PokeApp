package com.tzikin.pokeapp.data.network

sealed class RequestState<out T> {
    data class Success<out T>(val value: T) : RequestState<T>()
    data class Error(val errorMessage: String?) : RequestState<Nothing>()
    data class NetworkError(val errorResponse: ErrorResponse) : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
}