package com.tzikin.pokeapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.tzikin.pokeapp.core.di.IoDispatcher
import com.tzikin.pokeapp.core.di.MainDispatcher
import com.tzikin.pokeapp.data.database.entities.PokemonAndStat
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.database.entities.PokemonStat
import com.tzikin.pokeapp.data.database.entities.PokemonStatAndName
import com.tzikin.pokeapp.data.database.entities.PokemonStatName
import com.tzikin.pokeapp.data.model.PokemonStats
import com.tzikin.pokeapp.data.network.ApiResultHandle
import com.tzikin.pokeapp.data.network.RequestState
import com.tzikin.pokeapp.data.repository.PokemonRepository
import com.tzikin.pokeapp.domain.GetPokemonUseCase
import com.tzikin.pokeapp.domain.PokemonCallUseCase
import com.tzikin.pokeapp.domain.PokemonInformationUsecase
import com.tzikin.pokeapp.domain.SearchPokemonUsecase
import com.tzikin.pokeapp.domain.stat.GetPokemonStat
import com.tzikin.pokeapp.domain.stat.GetPokemonStatAndName
import com.tzikin.pokeapp.domain.stat.InsertPokemonStatNameUserCase
import com.tzikin.pokeapp.domain.stat.InsertStatUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    //val loginModel: LoginModel,
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    @IoDispatcher private val coroutineDispatcherIO: CoroutineDispatcher,
    private val pokemonCallUseCase: PokemonCallUseCase,
    private val pokemonInformationUsecase: PokemonInformationUsecase,
    val repository: PokemonRepository,
    private val getPokemonUseCase: GetPokemonUseCase,
    private val searchPokemonUsecase: SearchPokemonUsecase,
    private val insertPokemonStatUserCase: InsertStatUserCase,
    private val insertPokemonStatNameUserCase: InsertPokemonStatNameUserCase,
    private val getPokemonStatAndName: GetPokemonStatAndName,
    private val getPokemonStat: GetPokemonStat
) : ViewModel() {

    private var _pokemons = MutableLiveData<List<PokemonEntity>>()
    val pokemons: LiveData<List<PokemonEntity>> = _pokemons

    private var _myPokes = MutableLiveData<PokemonEntity>()
    val myPokes: LiveData<PokemonEntity> = _myPokes

    private var _pokeFound = MutableLiveData<PokemonEntity>()
    val pokeFound: LiveData<PokemonEntity> = _pokeFound

    private var _statAndNameList = MutableLiveData<List<PokemonStatAndName>>()
    var statAndNameList: LiveData<List<PokemonStatAndName>> = _statAndNameList

    private var _pokemonAndStatList = MutableLiveData<List<PokemonAndStat>>()
    var pokemonAndStatList: LiveData<List<PokemonAndStat>> = _pokemonAndStatList

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
                        val idResult = repository.insert(value)
                        _myPokes.value = value


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

    fun insertStats(values: List<PokemonStats>) {
        viewModelScope.launch {
            values.forEach { stat ->
                insertPokemonStatUserCase.invoke(
                    PokemonStat(
                        baseStat = stat.baseStat,
                        effort = stat.effort,
                        pokemonId = 1
                    )
                )
                insertPokemonStatNameUserCase.invoke(PokemonStatName(name = stat.stat.statName))
            }
        }
    }

    fun getStatAndName(id: Long) {
        viewModelScope.launch {
            _statAndNameList.value = getPokemonStatAndName.invoke(id)
        }
    }

    fun getPokemonStat() {
        viewModelScope.launch {
            _pokemonAndStatList.value = getPokemonStat.invoke()
        }
    }
}