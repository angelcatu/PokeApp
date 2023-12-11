package com.tzikin.pokeapp.domain.stat

import com.tzikin.pokeapp.data.repository.StatRepository
import javax.inject.Inject

class GetPokemonStatAndName @Inject constructor(
    private val statRepository: StatRepository
) {
    suspend operator fun invoke(id: Long) = statRepository.getPokemonStatAndName(id)
}