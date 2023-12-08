package com.tzikin.pokeapp.data.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    @SerializedName("next") val next: String,
    @SerializedName("previous") val previous: String,
    @SerializedName("results") val results: List<Pokemon>
)
