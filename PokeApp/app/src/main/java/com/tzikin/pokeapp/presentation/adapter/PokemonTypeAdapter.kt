package com.tzikin.pokeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tzikin.pokeapp.data.database.entities.PokemonTypeNameEntity
import com.tzikin.pokeapp.databinding.PokemoyTypeLayoutBinding

class PokemonTypeAdapter(
    private val typeList: MutableList<PokemonTypeNameEntity> = mutableListOf()
) : RecyclerView.Adapter<PokemonTypeAdapter.ViewHolder>() {
    class ViewHolder(val binding: PokemoyTypeLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonTypeWithNames: PokemonTypeNameEntity) {
            binding.txtType.text = pokemonTypeWithNames.name
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = PokemoyTypeLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(typeList[position])
    }

    override fun getItemCount(): Int = typeList.size

    fun addAllTypes(values: List<PokemonTypeNameEntity>) {
        typeList.clear()
        typeList.addAll(values)
        notifyDataSetChanged()
    }
}