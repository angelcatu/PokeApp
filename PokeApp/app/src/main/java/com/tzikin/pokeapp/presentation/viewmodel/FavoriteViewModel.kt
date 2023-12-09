package com.tzikin.pokeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.data.database.entities.FavoritePokemon
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.domain.GetAllFavoritesUseCase
import com.tzikin.pokeapp.domain.GetPokemonById
import com.tzikin.pokeapp.domain.SearchPokemonUsecase
import com.tzikin.pokeapp.domain.DeleteFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getAllFavoritesUseCase: GetAllFavoritesUseCase,
    private val getPokemonById: GetPokemonById,
    private val searchPokemonUsecase: SearchPokemonUsecase,
    private val deleteFavoriteUseCase: DeleteFavoriteUseCase
) : ViewModel() {

    private var _myFavoritesPokesList = MutableLiveData<List<FavoritePokemon>>()
    val myFavoritePokesList: LiveData<List<FavoritePokemon>> = _myFavoritesPokesList

    private var _myPokes = MutableLiveData<PokemonEntity>()
    val myPokes: LiveData<PokemonEntity> = _myPokes

    private var _pokeFound = MutableLiveData<PokemonEntity>()
    val pokeFound: LiveData<PokemonEntity> = _pokeFound

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

    fun deleteFavorite(id: Int) {
        viewModelScope.launch {
            deleteFavoriteUseCase.invoke(id)
        }
    }

    fun searchPokemon(id: Int, name: String){
        viewModelScope.launch {
            _pokeFound.value = searchPokemonUsecase.invoke(id, name)
        }
    }
}