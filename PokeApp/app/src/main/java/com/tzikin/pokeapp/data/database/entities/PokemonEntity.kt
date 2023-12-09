package com.tzikin.pokeapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tzikin.pokeapp.data.model.Stat

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "pokemon_name") val pokemonName: String,
    @ColumnInfo(name = "pokemon_number") val number: Int,
    @ColumnInfo(name = "pokemon_image") val pokemonImage: String,
    @ColumnInfo(name = "pokemon_shiny_image") val pokemonShinyImage: String,
    @ColumnInfo(name = "pokemon_type") val type: String,
    @ColumnInfo(name = "pokemon_base_stat")val baseStat: Int,
    @ColumnInfo(name = "pokemon_effort")val effort: Int,
    @ColumnInfo(name = "pokemon_stat")val stat: Int,
    @ColumnInfo(name = "pokemon_stat_name")val statName: String

)

@Entity(tableName = "favorite_table")
data class FavoritePokemon(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "pokemon_favorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "pokemon_number") val idPokemon: Int
)

