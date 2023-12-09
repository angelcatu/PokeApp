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

    suspend fun isFavorite(id: Int) =
        pokemonDao.getFavoritePokemon(id)

    suspend fun updateFavorite(id: Int, value: Boolean) {
        pokemonDao.updateFavoritePokemon(id, value)
    }

    suspend fun getAllFavorites() =
        pokemonDao.getAllFavoritesPokemon()


}