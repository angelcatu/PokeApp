package com.tzikin.pokeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tzikin.pokeapp.data.database.dao.PokemonDao
import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import com.tzikin.pokeapp.data.database.entities.PokemonEntity

@Database(entities = [PokemonEntity::class, FavoritePokemon::class], version = 1)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao

}