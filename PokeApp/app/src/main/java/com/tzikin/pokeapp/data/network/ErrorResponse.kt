package com.tzikin.pokeapp.data.network

import java.io.Serializable

data class ErrorResponse(
    // TODO: WHEN THE BACKEND GIVES THE END POINTS ADD THE ERROR RESPONSE VALUES HERE
    val errorMessage: String? = null,
    // login and another error response
    val error: String? = null,
    val error_description: String? = null,
    // general response
) : Serializable {
    /**
     * Return an error message if exists, only used when the variable with the message is unknown or
     * the message could be in different variables
     */
    fun getUnknownErrorMessage(): String =
        errorMessage ?: error ?: error_description
        ?: "Unexpected Error"
}