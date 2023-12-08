package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.model.PokemonResponse
import com.tzikin.pokeapp.data.network.ApiResultHandle
import com.tzikin.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class PokemonCallUseCase @Inject constructor(
    private val pokemonRepository: PokemonRepository
) {

    suspend operator fun invoke(): ApiResultHandle<PokemonResponse?> =
        pokemonRepository.getPokemonList()
}