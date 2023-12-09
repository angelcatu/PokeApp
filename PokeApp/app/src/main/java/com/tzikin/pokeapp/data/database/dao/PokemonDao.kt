package com.tzikin.pokeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tzikin.pokeapp.data.database.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table ORDER BY pokemon_name DESC")
    suspend fun getAllPokemon():List<PokemonEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon:List<PokemonEntity>)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAllPokemon()

    @Insert
    suspend fun insert(pokemon: PokemonEntity)

}