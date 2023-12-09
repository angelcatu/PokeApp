package com.tzikin.pokeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.databinding.PokemonListLayoutBinding

class PokemonAdapter(
    private val list: MutableList<PokemonEntity>
): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private lateinit var onCardClickListener: (PokemonEntity) -> Unit

    fun onCardClickListener(event: (PokemonEntity)-> Unit) {
        this.onCardClickListener = event
    }

    class ViewHolder(var binding: PokemonListLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemonEntity: PokemonEntity, onCardClickListener: (PokemonEntity) -> Unit) {

            binding.pokemonNumber.text = pokemonEntity.number.toString()
            binding.pokemonName.text = pokemonEntity.pokemonName
            Glide.with(binding.root).load(pokemonEntity.pokemonImage).centerCrop().into(binding.pokemonIMG)

            binding.card.setOnClickListener {
                onCardClickListener.invoke(pokemonEntity)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PokemonListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onCardClickListener)
    }
}