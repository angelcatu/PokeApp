package com.tzikin.pokeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tzikin.pokeapp.data.database.entities.PokemonStatAndName
import com.tzikin.pokeapp.databinding.PokemonStatLayoutBinding

class PokemonStatAdapter(
    private val statsAndNames: MutableList<PokemonStatAndName> = mutableListOf()
) : RecyclerView.Adapter<PokemonStatAdapter.ViewHolder>() {
    class ViewHolder(val binding: PokemonStatLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonStatAndName: PokemonStatAndName) {
            binding.txtStatName.text = pokemonStatAndName.pokemonStatName.name
            binding.progressBar.setProgress(pokemonStatAndName.pokemonStat.baseStat, true)
            binding.value.text = pokemonStatAndName.pokemonStat.baseStat.toString()
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding =
            PokemonStatLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(statsAndNames[position])
    }

    override fun getItemCount(): Int = statsAndNames.size

    fun addAllStats(values: List<PokemonStatAndName>) {
        statsAndNames.clear()
        statsAndNames.addAll(values)
        notifyDataSetChanged()
    }
}