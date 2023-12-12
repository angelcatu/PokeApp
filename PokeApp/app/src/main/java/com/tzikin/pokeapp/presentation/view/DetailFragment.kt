package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.databinding.FragmentDetailBinding
import com.tzikin.pokeapp.presentation.adapter.PokemonTypeAdapter
import com.tzikin.pokeapp.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_detail

    private lateinit var adapter: PokemonTypeAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("pokeID")
        id?.let {
            viewModel.getAllPokemonById(it)
            viewModel.getPokemonTypeList(id)
        }

        initRecyclerView()

        viewModel.pokemon.observe(requireActivity()) {
            binding.pokeName.text = it.pokemonName
            binding.pokeNumber.text = it.number.toString()
            Glide.with(requireActivity()).load(it.pokemonShinyImage).centerCrop()
                .into(binding.shinyImage)
        }

        viewModel.pokemonWithType.observe(requireActivity()) {
            adapter.addAllTypes(it[0].pokemonTypeName)
        }

    }

    private fun initRecyclerView() {
        adapter = PokemonTypeAdapter()
        binding.recyclerViewTypes.layoutManager = GridLayoutManager(requireActivity(), 3)
        binding.recyclerViewTypes.adapter = adapter

    }

}