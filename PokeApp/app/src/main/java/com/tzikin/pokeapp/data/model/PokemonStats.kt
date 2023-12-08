package com.tzikin.pokeapp.data.model

import com.google.gson.annotations.SerializedName

data class PokemonStats(
    @SerializedName("base_stat") val baseStat: Int,
    @SerializedName("effort") val effort: Int,
    @SerializedName("stat") val stat: List<Stat>
)
