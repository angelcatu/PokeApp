package com.tzikin.pokeapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tzikin.pokeapp.data.database.dao.PokemonDao
import com.tzikin.pokeapp.data.database.dao.PokemonStatDao
import com.tzikin.pokeapp.data.database.dao.PokemonTypeDao
import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.database.entities.PokemonStat
import com.tzikin.pokeapp.data.database.entities.PokemonStatName
import com.tzikin.pokeapp.data.database.entities.PokemonTypeCrossName
import com.tzikin.pokeapp.data.database.entities.PokemonTypeEntity
import com.tzikin.pokeapp.data.database.entities.PokemonTypeNameEntity

@Database(entities = [
    PokemonEntity::class, FavoritePokemon::class, PokemonStat::class, PokemonStatName::class,
    PokemonTypeEntity::class, PokemonTypeNameEntity::class, PokemonTypeCrossName::class
                     ], version = 1, exportSchema = false)
abstract class PokemonDatabase: RoomDatabase() {
    abstract fun getPokemonDao(): PokemonDao
    abstract fun getPokemonStat(): PokemonStatDao
    abstract fun getPokemonTypeDao(): PokemonTypeDao

}