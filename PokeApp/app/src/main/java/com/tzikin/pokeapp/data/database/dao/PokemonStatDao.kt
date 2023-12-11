package com.tzikin.pokeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.tzikin.pokeapp.data.database.entities.PokemonAndStat
import com.tzikin.pokeapp.data.database.entities.PokemonStat
import com.tzikin.pokeapp.data.database.entities.PokemonStatAndName
import com.tzikin.pokeapp.data.database.entities.PokemonStatName

@Dao
interface PokemonStatDao {

    @Transaction
    @Query("SELECT * FROM pokemon_stat WHERE pokemonId = :id ")
    suspend fun getPokemonStatAndName(id: Long): List<PokemonStatAndName>

    @Transaction
    @Query("SELECT * FROM pokemon_table")
    suspend fun getPokemonStat(): List<PokemonAndStat>



    @Insert
    suspend fun insert(pokemon: PokemonStat)

    @Insert
    suspend fun insert(pokemon: PokemonStatName)
}