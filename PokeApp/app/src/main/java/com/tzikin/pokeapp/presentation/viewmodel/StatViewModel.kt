package com.tzikin.pokeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.data.database.entities.PokemonAndStat
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.database.entities.PokemonStatAndName
import com.tzikin.pokeapp.domain.GetPokemonById
import com.tzikin.pokeapp.domain.stat.GetPokemonStatAndName
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatViewModel @Inject constructor(
    private val getPokemonById: GetPokemonById,
    private val getPokemonStatAndName: GetPokemonStatAndName
): ViewModel() {

    private var _pokemon = MutableLiveData<List<PokemonStatAndName>>()
    val pokemon: LiveData<List<PokemonStatAndName>> = _pokemon


    fun getAllPokemonStat(id: Long) {
        viewModelScope.launch{
            _pokemon.value = getPokemonStatAndName.invoke(id)
        }
    }
}