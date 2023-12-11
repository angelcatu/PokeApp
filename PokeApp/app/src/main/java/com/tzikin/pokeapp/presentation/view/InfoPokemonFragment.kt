package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
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

    private val argsD: InfoPokemonFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hideToolbar(false)
        setToolbarTitle(getString(R.string.my_pokemon_information))

        viewModel.getFavoritePokemon(argsD.id)
        viewModel.getAllPokemonById(argsD.id)

        tabLayoutSetting()
        observers()
    }

    private fun observers(){

        viewModel.pokemon.observe(requireActivity()) {
            Glide.with(requireActivity()).load(it.pokemonImage).centerCrop().into(binding.pokeInfoImg)
        }

        binding.favoriteIc.setOnClickListener {
            if (viewModel.pokemonIsFavorite.value == true){
                viewModel.setFavorite(false)
                viewModel.updateFavoriteState(argsD.id.toLong())
                Toast.makeText(requireActivity(), getString(R.string.pokemon_deleted), Toast.LENGTH_SHORT).show()
            }else {
                viewModel.setFavorite(true)
                viewModel.insertFavoritePokemon(argsD.id.toLong())

                Toast.makeText(requireActivity(), getString(R.string.pokemon_added), Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.pokemonIsFavorite.observe(requireActivity()){
            if (it) {
                binding.favoriteIc.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_favorite))
            }else {
                binding.favoriteIc.setImageDrawable(ContextCompat.getDrawable(requireActivity(), R.drawable.ic_favorite_border))
            }
        }
    }
    private fun tabLayoutSetting(){
        val adapter = PokemonInformationAdapter(this, 2, argsD.id.toLong())
        binding.viewPagerContent.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerContent) { _, _ ->

        }.attach()

        binding.tabLayout.getTabAt(0)?.text = requireActivity().getString(R.string.tab_one)
        binding.tabLayout.getTabAt(1)?.text = requireActivity().getText(R.string.tab_two)
    }
}