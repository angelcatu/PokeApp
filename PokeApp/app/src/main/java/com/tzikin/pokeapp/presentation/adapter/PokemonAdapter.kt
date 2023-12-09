package com.tzikin.pokeapp.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tzikin.pokeapp.data.database.entities.PokemonEntity
import com.tzikin.pokeapp.databinding.PokemonListLayoutBinding

class PokemonAdapter(
    private val list: MutableList<PokemonEntity>,
    private val fromFavoriteScreen: Boolean = false
): RecyclerView.Adapter<PokemonAdapter.ViewHolder>() {

    private lateinit var onCardClickListener: (PokemonEntity) -> Unit

    fun onCardClickListener(event: (PokemonEntity)-> Unit) {
        this.onCardClickListener = event
    }

    private lateinit var onFavoriteClickListener: (PokemonEntity, Int) -> Unit

    fun onFavoriteClickListener(event: (PokemonEntity, Int)-> Unit) {
        this.onFavoriteClickListener = event
    }

    class ViewHolder(var binding: PokemonListLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(
            pokemonEntity: PokemonEntity,
            onCardClickListener: (PokemonEntity) -> Unit,
            onFavoriteClickListener: (PokemonEntity, Int) -> Unit,
            fromFavoriteScreen: Boolean
        ) {

            if (fromFavoriteScreen) binding.favoriteIcon.visibility = View.VISIBLE
            else binding.favoriteIcon.visibility = View.GONE

            binding.pokemonNumber.text = pokemonEntity.type
            binding.pokemonName.text = pokemonEntity.pokemonName
            Glide.with(binding.root).load(pokemonEntity.pokemonImage).centerCrop().into(binding.pokemonIMG)

            binding.card.setOnClickListener {
                onCardClickListener.invoke(pokemonEntity)
            }

            binding.favoriteIcon.setOnClickListener {
                onFavoriteClickListener.invoke(pokemonEntity, layoutPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PokemonListLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], onCardClickListener, onFavoriteClickListener, fromFavoriteScreen)
    }

    fun insertElement(element: PokemonEntity) {
        list.add(element)
        notifyItemInserted(list.size)
        notifyDataSetChanged()
    }

    fun insertAll(elements: MutableList<PokemonEntity>) {
        list.clear()
        list.addAll(elements)
        notifyDataSetChanged()
    }

    fun deleteFavorite(position: Int){
        list.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }
}