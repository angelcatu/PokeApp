package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {

    suspend operator fun invoke() =
        repository.getAllPokemonFromDB()

}