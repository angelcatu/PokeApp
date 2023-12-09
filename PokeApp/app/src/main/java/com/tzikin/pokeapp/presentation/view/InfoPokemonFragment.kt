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

    private val args: InfoPokemonFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getFavoritePokemon(args.id)
        viewModel.getAllPokemonById(args.id)

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
                viewModel.updateFavoriteState(args.id)
                Toast.makeText(requireActivity(), getString(R.string.pokemon_deleted), Toast.LENGTH_SHORT).show()
            }else {
                viewModel.setFavorite(true)
                viewModel.insertFavoritePokemon(args.id)

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
        val adapter = PokemonInformationAdapter(this, 2, args.id)
        binding.viewPagerContent.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPagerContent) { tab, position ->

        }.attach()

        binding.tabLayout.getTabAt(0)?.text = requireActivity().getString(R.string.tab_one)
        binding.tabLayout.getTabAt(1)?.text = requireActivity().getText(R.string.tab_two)
    }
}