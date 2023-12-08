package com.tzikin.pokeapp.presentation.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.data.network.RequestState
import com.tzikin.pokeapp.databinding.FragmentPokemonListBinding
import com.tzikin.pokeapp.presentation.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : BaseFragment<FragmentPokemonListBinding>() {

    private val viewModel: PokemonListViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_pokemon_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getPokemonList()
    }

    private fun getPokemonList() {
        viewModel.getPokemon().observe(requireActivity()) { response ->
            when (response) {
                is RequestState.Loading -> {
                    showProgressBar()
                }

                is RequestState.Success -> {
                    hideProgressBar()
                }

                is RequestState.Error -> {
                    hideProgressBar()
                }

                is RequestState.NetworkError -> {
                    hideProgressBar()
                }
            }
        }
    }

}