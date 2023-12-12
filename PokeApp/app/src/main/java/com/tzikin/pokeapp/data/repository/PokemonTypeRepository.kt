package com.tzikin.pokeapp.data.repository

import com.tzikin.pokeapp.data.database.dao.PokemonTypeDao
import com.tzikin.pokeapp.data.database.entities.PokemonTypeCrossName
import com.tzikin.pokeapp.data.database.entities.PokemonTypeEntity
import com.tzikin.pokeapp.data.database.entities.PokemonTypeNameEntity
import javax.inject.Inject

class PokemonTypeRepository @Inject constructor(
    private val pokemonTypeDao: PokemonTypeDao
) {

    suspend fun insertPokemonType(pokemonTypeEntity: PokemonTypeEntity) =
        pokemonTypeDao.insertPokemonType(pokemonTypeEntity)


    suspend fun insertPokemonTypeName(pokemonTypeNameEntity: PokemonTypeNameEntity) =
        pokemonTypeDao.insertPokemonTypeName(pokemonTypeNameEntity)


    suspend fun getTypeWithPokemon(id: Long) =
        pokemonTypeDao.getTypeWithPokemon(id)

    suspend fun getPokemonWithType(id: Long) =
        pokemonTypeDao.getPokemonWithType(id)

    suspend fun getAllPokemonType() = pokemonTypeDao.getAllPokemonType()
    suspend fun getCrossPokemonType() = pokemonTypeDao.getCrossTypePokemon()

    suspend fun insertPokemonCross(pokemonTypeCrossName: PokemonTypeCrossName){
        pokemonTypeDao.insertPokemonTypeNameReference(pokemonTypeCrossName)
    }

}