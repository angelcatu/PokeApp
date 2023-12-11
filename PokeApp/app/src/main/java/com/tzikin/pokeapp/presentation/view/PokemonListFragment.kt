package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.data.model.Pokemon
import com.tzikin.pokeapp.data.network.RequestState
import com.tzikin.pokeapp.databinding.FragmentPokemonListBinding
import com.tzikin.pokeapp.presentation.adapter.PokemonAdapter
import com.tzikin.pokeapp.presentation.viewmodel.PokemonListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PokemonListFragment : BaseFragment<FragmentPokemonListBinding>() {

    private val viewModel: PokemonListViewModel by viewModels()

    private lateinit var adapter: PokemonAdapter

    override val layoutId: Int
        get() = R.layout.fragment_pokemon_list

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbar(false)
        setToolbarTitle(getString(R.string.my_pokes_title))

        viewModel.getAllPokemon()

        binding.fab.setOnClickListener {
            //navigateTo(PokemonListFragmentDirections.actionPokemonListFragmentToFavoriteFragment())
            viewModel.getStatAndName(50)
            viewModel.getPokemonStat()
        }

        viewModel.statAndNameList.observe(viewLifecycleOwner) {
            Log.i("ListaStat", it.toString())
        }

        viewModel.pokemonAndStatList.observe(viewLifecycleOwner) {
            Log.i("ListaPokemonStat", it.toString())
        }

        viewModel.myPokes.observe(viewLifecycleOwner) {
            adapter.insertElement(it)
        }

        viewModel.pokemons.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                getPokemonList()
            } else {
                adapter.insertAll(it as MutableList<PokemonEntity>)
            }
        }

        viewModel.pokeFound.observe(viewLifecycleOwner){
            if (it!=null) {
                adapter.insertAll(mutableListOf(it))
            }else {
                viewModel.getAllPokemon()
            }
        }

        adapter = PokemonAdapter(mutableListOf())
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerView.adapter = adapter

        adapter.onCardClickListener { entity ->
            navigateTo(
                PokemonListFragmentDirections.actionPokemonListFragmentToInfoPokemonFragment(
                    entity.id
                )
            )
        }
        adapter.onFavoriteClickListener { _, _ ->  }

        searchView()
    }

    private fun searchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val isDigit = newText?.isDigitsOnly()
                if (isDigit == true && newText != "") {
                    viewModel.searchPokemon(newText.toInt(), newText.toString())
                } else {
                    viewModel.searchPokemon(-1, newText.toString())
                }

                return true
            }
        })
    }

    private fun getPokemonList() {
        viewModel.getPokemon().observe(requireActivity()) { response ->
            when (response) {
                is RequestState.Loading -> {
                    showProgressBar()
                }

                is RequestState.Success -> {
                    response.value.let {
                        it?.results?.let { it1 -> callToPokemonInformation(it1) }
                    }
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

    private fun callToPokemonInformation(results: List<Pokemon>) {
        results.forEach {
            viewModel.getPokemonInformation(it.name).observe(requireActivity()) { response ->
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
}