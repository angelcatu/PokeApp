package com.tzikin.pokeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.domain.GetAllFavoritesUseCase
import com.tzikin.pokeapp.domain.GetPokemonById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val getPokemonById: GetPokemonById
) : ViewModel() {

    private var _myFavoritesPokesList = MutableLiveData<List<FavoritePokemon>>()
    val myFavoritePokesList: LiveData<List<FavoritePokemon>> = _myFavoritesPokesList

    private var _myPokes = MutableLiveData<PokemonEntity>()
    val myPokes: LiveData<PokemonEntity> = _myPokes

    fun getAllFavorites() {
        viewModelScope.launch {
            _myFavoritesPokesList.value = getAllFavoritesUseCase.invoke()
        }
    }

    fun getPokemonById(id: Int) {
        viewModelScope.launch {
           _myPokes.value = getPokemonById.invoke(id)

        }
    }
}