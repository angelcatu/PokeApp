package com.tzikin.pokeapp.domain.stat

import com.tzikin.pokeapp.data.database.dao.PokemonStatDao
import javax.inject.Inject

class GetPokemonStat @Inject constructor(
    private val dao: PokemonStatDao
) {

    suspend operator fun invoke() = dao.getPokemonStat()
}