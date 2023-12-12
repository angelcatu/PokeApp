package com.tzikin.pokeapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "pokemon_type")
data class PokemonTypeEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idType") val id: Long = 0,
    @ColumnInfo("slot") val slot: Int
)

@Entity(tableName = "pokemon_type_name")
data class PokemonTypeNameEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idTypeName") val id: Long = 0,
    @ColumnInfo("name") val name: String,
    @ColumnInfo("url") val url: String,

    )

// Relacion 1 a 1
data class PokemonTypeAndName(
    @Embedded val pokemonEntity: PokemonTypeEntity,
    @Relation(
        parentColumn = "idType",
        entityColumn = "idTypeName"
    )
    val pokemonTypeName: PokemonTypeNameEntity
)

// Relacion mucho a muchos
@Entity(primaryKeys = ["id", "idTypeName"])
data class PokemonTypeCrossName(
    val id: Long,
    val idTypeName: Long
)

data class PokemonWithTypes(
    @Embedded val pokemonEntity: PokemonEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "idTypeName",
        associateBy = Junction(PokemonTypeCrossName::class)
    )
    val pokemonTypeName: List<PokemonTypeNameEntity>
)

data class TypesWithPokemon(
    @Embedded val pokemonTypeEntity: PokemonTypeNameEntity,
    @Relation(
        parentColumn = "idTypeName",
        entityColumn = "id",
        associateBy = Junction(PokemonTypeCrossName::class)
    )
    val pokemonType: List<PokemonEntity>
)







