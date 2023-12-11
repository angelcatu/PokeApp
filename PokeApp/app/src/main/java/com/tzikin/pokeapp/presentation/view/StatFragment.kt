package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.databinding.FragmentStatBinding
import com.tzikin.pokeapp.presentation.adapter.PokemonStatAdapter
import com.tzikin.pokeapp.presentation.viewmodel.StatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StatFragment : BaseFragment<FragmentStatBinding>() {

    private val viewModel: StatViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_stat

    private lateinit var statAdapter: PokemonStatAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getLong("pokeID")
        id?.let { viewModel.getAllPokemonStat(it) }

        initRecyclerView()

        viewModel.pokemon.observe(viewLifecycleOwner) {
            statAdapter.addAllStats(it)
        }
    }

    private fun initRecyclerView() {
        statAdapter = PokemonStatAdapter()
        binding.recyclerViewStats.layoutManager = LinearLayoutManager(requireActivity())
        binding.recyclerViewStats.adapter = statAdapter
    }

}