package com.tzikin.pokeapp.domain.type

import com.tzikin.pokeapp.data.database.entities.PokemonTypeEntity
import com.tzikin.pokeapp.data.repository.PokemonTypeRepository
import javax.inject.Inject

class InsertPokemonTypeUseCase @Inject constructor(
    private val typeRepository: PokemonTypeRepository
) {

    suspend operator fun invoke(pokemonTypeEntity: PokemonTypeEntity) =
        typeRepository.insertPokemonType(pokemonTypeEntity)

}