package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideContext
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.databinding.FragmentDetailBinding
import com.tzikin.pokeapp.presentation.viewmodel.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel: DetailViewModel by viewModels()

    override val layoutId: Int
        get() = R.layout.fragment_detail

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("pokeID")
        id?.let { viewModel.getAllPokemonById(it) }

        viewModel.pokemon.observe(requireActivity()) {
            binding.pokeName.text = it.pokemonName
            binding.pokeNumber.text = it.number.toString()
            binding.pokeInfoType.text = it.type
            Glide.with(requireActivity()).load(it.pokemonShinyImage).centerCrop().into(binding.shinyImage)
        }

    }

}