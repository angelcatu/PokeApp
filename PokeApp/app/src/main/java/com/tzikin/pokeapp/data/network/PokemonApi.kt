package com.tzikin.pokeapp.data.network

import com.tzikin.pokeapp.data.model.PokemonInformationResponse
import com.tzikin.pokeapp.data.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface PokemonApi {

    @GET("pokemon/?limit=50&offset=50")
    suspend fun getPokemon(): PokemonResponse

    @GET("pokemon/{number}")
    suspend fun getPokemonInformation(@Path("number") pokemonNumber: String): PokemonInformationResponse

}