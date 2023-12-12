package com.tzikin.pokeapp.domain.type

import com.tzikin.pokeapp.data.database.entities.PokemonTypeNameEntity
import com.tzikin.pokeapp.data.repository.PokemonTypeRepository
import javax.inject.Inject

class InsertPokemonTypeNameUseCase @Inject constructor(
    private val typeRepository: PokemonTypeRepository
) {

    suspend operator fun invoke(pokemonTypeNameEntity: PokemonTypeNameEntity) =
        typeRepository.insertPokemonTypeName(pokemonTypeNameEntity)

}