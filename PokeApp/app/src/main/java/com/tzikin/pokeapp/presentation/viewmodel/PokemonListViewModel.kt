package com.tzikin.pokeapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.core.di.MainDispatcher
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.database.entities.PokemonStat
import com.tzikin.pokeapp.data.database.entities.PokemonStatName
import com.tzikin.pokeapp.data.database.entities.PokemonTypeCrossName
import com.tzikin.pokeapp.data.database.entities.PokemonTypeEntity
import com.tzikin.pokeapp.data.database.entities.PokemonTypeNameEntity
import com.tzikin.pokeapp.data.model.PokemonType
import com.tzikin.pokeapp.data.network.ApiResultHandle
import com.tzikin.pokeapp.data.network.RequestState
import com.tzikin.pokeapp.data.repository.PokemonRepository
import com.tzikin.pokeapp.data.repository.PokemonTypeRepository
import com.tzikin.pokeapp.domain.GetPokemonUseCase
import com.tzikin.pokeapp.domain.PokemonCallUseCase
import com.tzikin.pokeapp.domain.PokemonInformationUsecase
import com.tzikin.pokeapp.domain.SearchPokemonUsecase
import com.tzikin.pokeapp.domain.stat.InsertPokemonStatNameUserCase
import com.tzikin.pokeapp.domain.stat.InsertStatUserCase
import com.tzikin.pokeapp.domain.type.InsertPokemonTypeNameUseCase
import com.tzikin.pokeapp.domain.type.InsertPokemonTypeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val pokemonCallUseCase: PokemonCallUseCase,
    private val pokemonInformationUsecase: PokemonInformationUsecase,
    val repository: PokemonRepository,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val searchPokemonUsecase: SearchPokemonUsecase,
    private val insertPokemonStatUserCase: InsertStatUserCase,
    private val insertPokemonStatNameUserCase: InsertPokemonStatNameUserCase,
    private val insertPokemonTypeUseCase: InsertPokemonTypeUseCase,
    private val insertPokemonTypeNameUseCase: InsertPokemonTypeNameUseCase,
    private val typeRepository: PokemonTypeRepository
) : ViewModel() {

    private var _pokemons = MutableLiveData<List<PokemonEntity>>()
    val pokemons: LiveData<List<PokemonEntity>> = _pokemons

    private var _myPokes = MutableLiveData<PokemonEntity>()
    val myPokes: LiveData<PokemonEntity> = _myPokes

    private var _pokeFound = MutableLiveData<PokemonEntity>()
    val pokeFound: LiveData<PokemonEntity> = _pokeFound
    fun getPokemon() = liveData(viewModelScope.coroutineContext + coroutineDispatcher) {
        emit(RequestState.Loading)
        when (val response = pokemonCallUseCase.invoke()) {
            is ApiResultHandle.Success -> {
                repository.deleteAll()
                emit(RequestState.Success(response.value))
            }

            is ApiResultHandle.ApiError -> {
                emit(RequestState.Error(response.error.errorMessage))
            }

            is ApiResultHandle.NetworkError -> {
                emit(RequestState.Error(response.errorNetworkMessage.error))
            }
        }
    }

    fun getPokemonInformation(id: String) =
        liveData(viewModelScope.coroutineContext + coroutineDispatcher) {
            emit(RequestState.Loading)
            when (val response = pokemonInformationUsecase.invoke(id)) {
                is ApiResultHandle.Success -> {
                    response.value?.let {
                        val entity = PokemonEntity(
                            pokemonName = it.name,
                            number = it.id,
                            pokemonImage = it.images.frontImageUrl,
                            pokemonShinyImage = it.images.fronShinyImageUrl,
                            type = it.pokemonType[0].type.type,
                            baseStat = it.pokemonStats[0].baseStat,
                            effort = it.pokemonStats[0].effort,
                            stat = it.pokemonStats[0].baseStat,
                            statName = it.pokemonStats[0].stat.statName
                        )
                        val idResult = repository.insert(entity)
                        entity.id = idResult

                        _myPokes.value = entity


                        insertTypes(it.pokemonType, it.name, idResult)


                        it.pokemonStats.forEach { stat ->
                            insertPokemonStatUserCase.invoke(
                                PokemonStat(
                                    baseStat = stat.baseStat,
                                    effort = stat.effort,
                                    pokemonId = idResult
                                )
                            )
                            insertPokemonStatNameUserCase.invoke(PokemonStatName(name = stat.stat.statName))
                        }


                        //val d = getPokemonUseCase.invoke()

                    }
                    emit(RequestState.Success(response.value))
                }

                is ApiResultHandle.ApiError -> {
                    emit(RequestState.Error(response.error.errorMessage))
                }

                is ApiResultHandle.NetworkError -> {
                    emit(RequestState.Error(response.errorNetworkMessage.error))
                }
            }
        }

    fun getAllPokemon() {
        viewModelScope.launch {
            _pokemons.value = getPokemonUseCase.invoke()
        }
    }

    fun searchPokemon(id: Int, name: String) {
        viewModelScope.launch {
            _pokeFound.value = searchPokemonUsecase.invoke(id, name)
        }
    }

    private fun insertTypes(pokemonType2: List<PokemonType>, name: String, idResult: Long) {
        pokemonType2.forEach { pokemonType ->

            var idPokemonTypeName: Long = 0

            viewModelScope.launch {
                insertPokemonTypeUseCase.invoke(
                    PokemonTypeEntity(slot = pokemonType.slot)
                )
            }

            viewModelScope.launch {
                idPokemonTypeName = insertPokemonTypeNameUseCase.invoke(
                    PokemonTypeNameEntity(
                        name = pokemonType.type.type,
                        url = pokemonType.type.urlTypeInfo
                    )
                )
            }

            viewModelScope.launch {

                typeRepository.insertPokemonCross(
                    PokemonTypeCrossName(
                        id = idResult,
                        idTypeName = idPokemonTypeName
                    )
                )

                typeRepository.insertPokemonCross(
                    PokemonTypeCrossName(
                        id = idResult,
                        idTypeName = idPokemonTypeName
                    )
                )

                Log.i("type: ", pokemonType.type.type)
                Log.i("pokemon: ", name)


            }


        }
    }
}