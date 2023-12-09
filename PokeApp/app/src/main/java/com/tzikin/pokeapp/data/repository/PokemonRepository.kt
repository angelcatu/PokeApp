package com.tzikin.pokeapp.data.repository

import android.util.Log
import com.tzikin.pokeapp.core.di.IoDispatcher
import com.tzikin.pokeapp.data.database.dao.PokemonDao
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.model.PokemonInformationResponse
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
    private val pokemonDao: PokemonDao,
    @IoDispatcher private val coroutineDispatcher: CoroutineDispatcher
){

    companion object {
        private const val REQUEST_SUCCESSFUL = true
    }

    suspend fun insert(quotes:List<PokemonEntity>){
        pokemonDao.insertAll(quotes)
    }

    suspend fun insert(value: PokemonEntity) {
        pokemonDao.insert(value)
    }

    suspend fun deleteAll() {
        pokemonDao.deleteAllPokemon()
    }

    suspend fun getAllPokemonFromDB(): List<PokemonEntity> =
        pokemonDao.getAllPokemon()

    suspend fun getPokemonById(id: Int) : PokemonEntity =
        pokemonDao.getByPokemonId(id)

    suspend fun getPokemonList(): ApiResultHandle<PokemonResponse?> =
        withContext(coroutineDispatcher) {
            runCatching {
                pokemonApi.getPokemon()
            }
        }.toPokeListResult()

    suspend fun getPokemonInformation(id: String): ApiResultHandle<PokemonInformationResponse?> =
        withContext(coroutineDispatcher) {
            runCatching {
                pokemonApi.getPokemonInformation(id)
            }
        }.toPokeInfoResult()

    private fun Result<PokemonResponse?>.toPokeListResult(): ApiResultHandle<PokemonResponse?> =
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

    private fun Result<PokemonInformationResponse?>.toPokeInfoResult(): ApiResultHandle<PokemonInformationResponse?> =
        when (val result = getOrNull()) {
            null -> {
                ApiResultHandle.NetworkError(ErrorResponse(errorMessage = "Error al establecer una conexión con el servidor."))
            }
            result -> {
                when (result.id != -1) {
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