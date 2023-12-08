package com.tzikin.pokeapp.data.repository

import com.tzikin.pokeapp.core.di.IoDispatcher
import com.tzikin.pokeapp.data.model.PokemonResponse
import com.tzikin.pokeapp.data.network.ApiResultHandle
import com.tzikin.pokeapp.data.network.ErrorResponse
import com.tzikin.pokeapp.data.network.PokemonApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val pokemonApi: PokemonApi,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
){

    companion object {
        private const val REQUEST_SUCCESSFUL = true
    }

    suspend fun getPokemonList(): ApiResultHandle<PokemonResponse?> =
        withContext(coroutineDispatcher) {
            runCatching {
                pokemonApi.getPokemon()
            }
        }.toLoginResult()

    private fun Result<PokemonResponse?>.toLoginResult(): ApiResultHandle<PokemonResponse?> =
        when (val result = getOrNull()) {
            null -> {
                ApiResultHandle.NetworkError(ErrorResponse(errorMessage = "Error al establecer una conexión con el servidor."))
            }
            result -> {
                when (result.results.isNotEmpty()) {
                    REQUEST_SUCCESSFUL -> {
                        ApiResultHandle.Success(result)
                    }
                    else -> {
                        ApiResultHandle.ApiError(
                            "01",
                            ErrorResponse("Error", "Error")
                        )
                    }
                }
            }
            else -> {
                ApiResultHandle.NetworkError(ErrorResponse(errorMessage = "Error"))
            }
        }

}