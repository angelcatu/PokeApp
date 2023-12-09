package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.repository.FavoriteRepository
import javax.inject.Inject

class UpdateFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(id: Int, value: Boolean) {
        favoriteRepository.updateFavorite(id, value)
    }
}