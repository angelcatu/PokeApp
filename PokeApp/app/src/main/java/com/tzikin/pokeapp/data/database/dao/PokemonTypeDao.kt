package com.tzikin.pokeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.tzikin.pokeapp.data.database.entities.PokemonTypeCrossName
import com.tzikin.pokeapp.data.database.entities.PokemonTypeEntity
import com.tzikin.pokeapp.data.database.entities.PokemonTypeNameEntity
import com.tzikin.pokeapp.data.database.entities.PokemonWithTypes
import com.tzikin.pokeapp.data.database.entities.TypesWithPokemon

@Dao
interface PokemonTypeDao {

    @Transaction
    @Query("SELECT * FROM pokemon_type_name WHERE idTypeName = :id ")
    suspend fun getTypeWithPokemon(id: Long): List<TypesWithPokemon>

    @Transaction
    @Query("SELECT * FROM pokemon_table WHERE id = :id ")
    suspend fun getPokemonWithType(id: Long): List<PokemonWithTypes>


    @Insert
    suspend fun insertPokemonType(pokemonType: PokemonTypeEntity): Long

    @Insert
    suspend fun insertPokemonTypeName(pokemonType: PokemonTypeNameEntity): Long

    @Query("SELECT * FROM pokemon_type_name")
    suspend fun getAllPokemonType(): List<PokemonTypeNameEntity>

    @Query("SELECT * FROM PokemonTypeCrossName")
    suspend fun getCrossTypePokemon(): List<PokemonTypeCrossName>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPokemonTypeNameReference(pokemonTypeCrossName: PokemonTypeCrossName)
}