package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.model.PokemonInformationResponse
import com.tzikin.pokeapp.data.network.ApiResultHandle
import com.tzikin.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class PokemonInformationUsecase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {
    suspend operator fun invoke(id: String): ApiResultHandle<PokemonInformationResponse?> =
        pokemonRepository.getPokemonInformation(id)
}