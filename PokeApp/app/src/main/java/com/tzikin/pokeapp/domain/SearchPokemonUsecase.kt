package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class SearchPokemonUsecase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(id: Int, name: String) = repository.searchPokemonBy(id, name)

}