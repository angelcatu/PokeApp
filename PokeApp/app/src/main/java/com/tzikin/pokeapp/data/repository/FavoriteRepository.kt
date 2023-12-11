package com.tzikin.pokeapp.data.repository

import com.tzikin.pokeapp.data.database.dao.PokemonDao
import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import javax.inject.Inject

class FavoriteRepository @Inject constructor(
    private val pokemonDao: PokemonDao
) {

    suspend fun insertFavorite(element: FavoritePokemon){
        pokemonDao.insertFavorite(favoritePokemon = element)
    }

    suspend fun isFavorite(id: Long) =
        pokemonDao.getFavoritePokemon(id)

    suspend fun deleteFavorite(id: Long) {
        pokemonDao.deleteFavorite(id)
    }

    suspend fun getAllFavorites() =
        pokemonDao.getAllFavoritesPokemon()


}