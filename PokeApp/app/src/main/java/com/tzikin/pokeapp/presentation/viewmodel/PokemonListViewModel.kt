package com.tzikin.pokeapp.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.tzikin.pokeapp.core.di.MainDispatcher
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.network.ApiResultHandle
import com.tzikin.pokeapp.data.network.RequestState
import com.tzikin.pokeapp.data.repository.PokemonRepository
import com.tzikin.pokeapp.domain.GetPokemonUseCase
import com.tzikin.pokeapp.domain.PokemonCallUseCase
import com.tzikin.pokeapp.domain.PokemonInformationUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    //val loginModel: LoginModel,
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val pokemonCallUseCase: PokemonCallUseCase,
    private val pokemonInformationUsecase: PokemonInformationUsecase,
    val repository: PokemonRepository,
    private val getPokemonUseCase: GetPokemonUseCase
) : ViewModel() {

    private var _pokemons = MutableLiveData<List<PokemonEntity>>()
    val pokemons: LiveData<List<PokemonEntity>> = _pokemons

    private var _myPokes = MutableLiveData<PokemonEntity>()
    val myPokes: LiveData<PokemonEntity> = _myPokes
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

    fun getPokemonInformation(id: String) = liveData(viewModelScope.coroutineContext + coroutineDispatcher) {
        emit(RequestState.Loading)
        when (val response = pokemonInformationUsecase.invoke(id)) {
            is ApiResultHandle.Success -> {
                response.value?.let {
                    val value = PokemonEntity(
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
                    repository.insert(value)
                    _myPokes.value = value
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
}