package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.databinding.FragmentStatBinding
import com.tzikin.pokeapp.presentation.viewmodel.StatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatFragment : BaseFragment<FragmentStatBinding>() {

    private val viewModel: StatViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_stat

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt("pokeID")
        id?.let { viewModel.getAllPokemonById(it) }

        viewModel.pokemon.observe(requireActivity()){
            binding.progressBarHP.setProgress(it.baseStat, true)
            binding.progressBarAttk.setProgress(it.baseStat, true)
            binding.progressBarDefense.setProgress(it.baseStat, true)

            binding.valueHP.text = it.baseStat.toString()
            binding.valueAttack.text = it.baseStat.toString()
            binding.valueDefense.text = it.baseStat.toString()
        }
    }

}