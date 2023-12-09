package com.tzikin.pokeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.domain.GetPokemonById
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoPokemonViewModel @Inject constructor(
    private val getPokemonById: GetPokemonById
): ViewModel() {
    private var _pokemon = MutableLiveData<PokemonEntity>()
    val pokemon: LiveData<PokemonEntity> = _pokemon

    fun getAllPokemonById(id: Int) {
        viewModelScope.launch {
            _pokemon.value = getPokemonById.invoke(id)
        }
    }
}