package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
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

        viewModel.pokeFound.observe(viewLifecycleOwner){
            if (it!=null) {
                adapter.insertAll(mutableListOf(it))
            }else {
                viewModel.getAllFavorites()
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
                    viewModel.searchPokemon(-1, newText.toString())
                }

                return true
            }
        })
    }

    private fun initRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        adapter = PokemonAdapter(mutableListOf())
        binding.recyclerView.adapter = adapter

        adapter.onCardClickListener {  }
    }
}