package com.tzikin.pokeapp.domain.stat

import com.tzikin.pokeapp.data.database.entities.PokemonStatName
import com.tzikin.pokeapp.data.repository.StatRepository
import javax.inject.Inject

class InsertPokemonStatNameUserCase @Inject constructor(
    private val statRepository: StatRepository
) {
    suspend operator fun invoke(pokemonStatName: PokemonStatName){
        statRepository.insertPokemonStatName(pokemonStatName)
    }
}