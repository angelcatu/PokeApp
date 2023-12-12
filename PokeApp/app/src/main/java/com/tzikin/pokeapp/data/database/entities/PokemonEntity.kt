package com.tzikin.pokeapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "pokemon_table")
data class PokemonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") var id: Long = 0,
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

data class PokemonAndStat(
    @Embedded val pokemonEntity: PokemonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idStat"
    )
    val pokemonStats: List<PokemonStat>
)


@Entity(tableName = "favorite_table")
data class FavoritePokemon(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long = 0,
    @ColumnInfo(name = "pokemon_favorite") val isFavorite: Boolean = false,
    @ColumnInfo(name = "pokemon_number") val idPokemon: Long
)


// Stat entities
@Entity(tableName = "pokemon_stat")
data class PokemonStat(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idStat") val idStat: Long = 0,
    @ColumnInfo("base_stat") val baseStat: Int,
    @ColumnInfo("effort") val effort: Int,
    @ColumnInfo("pokemonId") val pokemonId: Long
)

data class PokemonStatAndName(
    @Embedded val pokemonStat: PokemonStat,
    @Relation(
        parentColumn = "idStat",
        entityColumn = "idStatName"
    )
    val pokemonStatName: PokemonStatName
)

@Entity(tableName = "pokemon_stat_name")
data class PokemonStatName(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idStatName") val idStatName: Long = 0,
    @ColumnInfo("name") val name: String
)
