package com.tzikin.pokeapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.core.di.MainDispatcher
import com.tzikin.pokeapp.data.network.ApiResultHandle
import com.tzikin.pokeapp.data.network.RequestState
import com.tzikin.pokeapp.domain.PokemonCallUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    //val loginModel: LoginModel,
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val pokemonCallUseCase: PokemonCallUseCase

) : ViewModel() {

    fun getPokemon() = liveData(viewModelScope.coroutineContext + coroutineDispatcher) {
        emit(RequestState.Loading)
        when (val response = pokemonCallUseCase.invoke()) {
            is ApiResultHandle.Success -> {
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
}