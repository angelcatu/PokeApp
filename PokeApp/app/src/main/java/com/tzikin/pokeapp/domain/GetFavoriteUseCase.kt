package com.tzikin.pokeapp.domain

import com.tzikin.pokeapp.data.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteUseCase @Inject constructor(
    private val favoriteRepository: FavoriteRepository
) {

    suspend operator fun invoke(id: Long): Boolean {
       val value = favoriteRepository.isFavorite(id)
        return value?.isFavorite ?: false
    }


}