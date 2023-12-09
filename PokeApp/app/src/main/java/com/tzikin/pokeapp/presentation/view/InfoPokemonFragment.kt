package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.databinding.FragmentInfoPokemonBinding
import com.tzikin.pokeapp.presentation.adapter.PokemonInformationAdapter
import com.tzikin.pokeapp.presentation.viewmodel.InfoPokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InfoPokemonFragment : BaseFragment<FragmentInfoPokemonBinding>() {

    private val viewModel: InfoPokemonViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_info_pokemon

    private val args: InfoPokemonFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PokemonInformationAdapter(this, 2, args.id)
        binding.viewPagerContent.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerContent) { tab, position ->

        }.attach()

        binding.tabLayout.getTabAt(0)?.text = requireActivity().getString(R.string.tab_one)
        binding.tabLayout.getTabAt(1)?.text = requireActivity().getText(R.string.tab_two)


        viewModel.getAllPokemonById(args.id)
        viewModel.pokemon.observe(requireActivity()) {
            Glide.with(requireActivity()).load(it.pokemonImage).centerCrop().into(binding.pokeInfoImg)
        }
    }

}