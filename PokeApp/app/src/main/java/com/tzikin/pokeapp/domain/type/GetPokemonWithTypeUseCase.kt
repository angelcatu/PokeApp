package com.tzikin.pokeapp.domain.type

import com.tzikin.pokeapp.data.repository.PokemonTypeRepository
import javax.inject.Inject

class GetPokemonWithTypeUseCase @Inject constructor(
    private val typeRepository: PokemonTypeRepository
) {

    suspend operator fun invoke(id: Long) = typeRepository.getPokemonWithType(id)
}