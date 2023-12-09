package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import com.tzikin.pokeapp.data.repository.FavoriteRepository
import javax.inject.Inject

class InsertFavoriteUsecase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {
    suspend operator fun invoke(favoritePokemon: FavoritePokemon){
        favoriteRepository.insertFavorite(favoritePokemon)
    }
}