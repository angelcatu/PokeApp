package com.tzikin.pokeapp.presentation.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.domain.GetFavoriteUseCase
import com.tzikin.pokeapp.domain.GetPokemonById
import com.tzikin.pokeapp.domain.InsertFavoriteUsecase
import com.tzikin.pokeapp.domain.UpdateFavoriteUseCase
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoPokemonViewModel @Inject constructor(
    private val getPokemonById: GetPokemonById,
    private val updateFavoriteUseCase: UpdateFavoriteUseCase,
    private val getFavoriteUseCase: GetFavoriteUseCase,
    private val insertFavoriteUsecase: InsertFavoriteUsecase
): ViewModel() {
    private var _pokemon = MutableLiveData<PokemonEntity>()
    val pokemon: LiveData<PokemonEntity> = _pokemon

    private var _pokemonIsFavorite = MutableLiveData(false)
    var pokemonIsFavorite: LiveData<Boolean> = _pokemonIsFavorite


    fun setFavorite(value: Boolean) {
        _pokemonIsFavorite.value = value
    }
    fun getAllPokemonById(id: Int) {
        viewModelScope.launch {
            _pokemon.value = getPokemonById.invoke(id)
        }
    }

    fun insertFavoritePokemon(id: Int) {
        viewModelScope.launch {
            insertFavoriteUsecase.invoke(FavoritePokemon(
                isFavorite = true,
                idPokemon = id
            ))
        }
    }
    fun updateFavoriteState(id: Int, value: Boolean) {
        viewModelScope.launch {
            updateFavoriteUseCase.invoke(id, value)
        }
    }

    fun getFavoritePokemon(id: Int) {
        viewModelScope.launch {
           _pokemonIsFavorite.value = getFavoriteUseCase.invoke(id)
        }
    }

    fun onClick(view: View) {

    }
}