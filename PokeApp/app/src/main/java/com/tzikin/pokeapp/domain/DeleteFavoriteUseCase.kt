package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.repository.FavoriteRepository
import javax.inject.Inject

class DeleteFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(id: Long) {
        favoriteRepository.deleteFavorite(id)
    }
}