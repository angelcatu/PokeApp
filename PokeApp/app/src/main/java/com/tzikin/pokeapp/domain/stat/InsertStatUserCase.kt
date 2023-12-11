package com.tzikin.pokeapp.domain.stat

import com.tzikin.pokeapp.data.database.entities.PokemonStat
import com.tzikin.pokeapp.data.repository.StatRepository
import javax.inject.Inject

class InsertStatUserCase @Inject constructor(
    private val statRepository: StatRepository
) {

    suspend operator fun invoke(pokemonStat: PokemonStat) {
        statRepository.insertPokemonStat(pokemonStat)
    }
}