package com.tzikin.pokeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import com.tzikin.pokeapp.data.database.entities.PokemonEntity

@Dao
interface PokemonDao {

    @Query("SELECT * FROM pokemon_table ORDER BY pokemon_name DESC")
    suspend fun getAllPokemon():List<PokemonEntity>

    @Query("SELECT * FROM pokemon_table where pokemon_number = :id")
    suspend fun getByPokemonId(id: Int): PokemonEntity

    @Query("SELECT * FROM pokemon_table where pokemon_number = :id OR pokemon_name = :name" )
    suspend fun searchPokemonBy(id: Int, name: String): PokemonEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(pokemon:List<PokemonEntity>)

    @Query("DELETE FROM pokemon_table")
    suspend fun deleteAllPokemon()

    @Insert
    suspend fun insert(pokemon: PokemonEntity)

    // Favorites
    @Insert
    suspend fun insertFavorite(favoritePokemon: FavoritePokemon)

    @Query("SELECT * FROM favorite_table where pokemon_number = :pokeID")
    suspend fun getFavoritePokemon(pokeID: Int): FavoritePokemon?

    @Query("UPDATE favorite_table SET pokemon_favorite = :newValue WHERE pokemon_number = :pokeID ")
    suspend fun updateFavoritePokemon(pokeID: Int, newValue: Boolean)

    @Query("DELETE FROM favorite_table WHERE pokemon_number = :id")
    suspend fun deleteFavorite(id: Int)

    @Query("SELECT * FROM favorite_table")
    suspend fun getAllFavoritesPokemon():List<FavoritePokemon>

}