package com.tzikin.pokeapp.data.repository

import com.tzikin.pokeapp.data.database.dao.PokemonStatDao
import com.tzikin.pokeapp.data.database.entities.PokemonStat
import com.tzikin.pokeapp.data.database.entities.PokemonStatName
import javax.inject.Inject

class StatRepository @Inject constructor(
    private val statDao: PokemonStatDao
) {

    suspend fun insertPokemonStat(pokemonStat: PokemonStat) {
        statDao.insert(pokemonStat)
    }

    suspend fun insertPokemonStatName(pokemonStatName: PokemonStatName) {
        statDao.insert(pokemonStatName)
    }

    suspend fun getPokemonStatAndName(id: Long) = statDao.getPokemonStatAndName(id)

    suspend fun getPokemonStat() = statDao.getPokemonStat()
}