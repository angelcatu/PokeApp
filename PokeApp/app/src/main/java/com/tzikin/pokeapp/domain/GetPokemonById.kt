package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonById @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke(id: Long) =
        repository.getPokemonById(id)

}