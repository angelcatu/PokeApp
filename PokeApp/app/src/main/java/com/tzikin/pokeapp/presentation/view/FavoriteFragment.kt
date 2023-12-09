package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.databinding.FragmentFavoriteBinding
import com.tzikin.pokeapp.presentation.adapter.PokemonAdapter
import com.tzikin.pokeapp.presentation.adapter.PokemonInformationAdapter
import com.tzikin.pokeapp.presentation.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>() {

    private val viewModel: FavoriteViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_favorite

    private lateinit var adapter: PokemonAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbar(false)
        setToolbarTitle(getString(R.string.favorites_title))

        initRecyclerView()
        viewModel.getAllFavorites()

        viewModel.myFavoritePokesList.observe(requireActivity()) {

            adapter.clearList()
            if (it.isNotEmpty()) {
                it.forEach { favoriteId ->
                    viewModel.getPokemonById(favoriteId.idPokemon)
                }
            } else {
                Toast.makeText(
                    requireActivity(),
                    getString(R.string.no_favorites),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.myPokes.observe(requireActivity()) {
            adapter.insertElement(it)
        }

        viewModel.pokeFound.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.insertAll(mutableListOf(it))
            }
        }

        binding.searchView2.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val isDigit = newText?.isDigitsOnly()
                if (isDigit == true && newText != "") {
                    viewModel.searchPokemon(newText.toInt(), newText.toString())
                } else {
                    if (newText.toString() != "")
                        viewModel.searchPokemon(-1, newText.toString())
                    else viewModel.getAllFavorites()
                }

                return true
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = PokemonAdapter(mutableListOf(), true)
        binding.recyclerView.adapter = adapter

        adapter.onCardClickListener { }

        adapter.onFavoriteClickListener { it, position ->
            viewModel.deleteFavorite(it.number)
            adapter.deleteFavorite(position)

            Toast.makeText(
                requireActivity(),
                getString(R.string.pokemon_deleted),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}