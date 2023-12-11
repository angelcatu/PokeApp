package com.tzikin.pokeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.domain.GetPokemonById
import com.tzikin.pokeapp.domain.GetPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getPokemonById: GetPokemonById
) : ViewModel() {

    private var _pokemon = MutableLiveData<PokemonEntity>()
    val pokemon: LiveData<PokemonEntity> = _pokemon

    fun getAllPokemonById(id: Long) {
        viewModelScope.launch {
            _pokemon.value = getPokemonById.invoke(id)
        }
    }

}