package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
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


        initRecyclerView()
        viewModel.getAllFavorites()

        viewModel.myFavoritePokesList.observe(requireActivity()) {
            it.forEach {favoriteId ->
                viewModel.getPokemonById(favoriteId.idPokemon)
            }
        }

        viewModel.myPokes.observe(requireActivity()) {
            adapter.insertElement(it)
        }
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = PokemonAdapter(mutableListOf())
        binding.recyclerView.adapter = adapter

        adapter.onCardClickListener {  }
    }
}