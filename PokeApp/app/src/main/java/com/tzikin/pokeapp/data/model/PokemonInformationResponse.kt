package com.tzikin.pokeapp.data.model

import com.google.gson.annotations.SerializedName

data class PokemonInformationResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("sprites") val images: Sprite,
    @SerializedName("types") val pokemonType: List<PokemonType>,
    @SerializedName("stats") val pokemonStats: List<PokemonStats>

)
