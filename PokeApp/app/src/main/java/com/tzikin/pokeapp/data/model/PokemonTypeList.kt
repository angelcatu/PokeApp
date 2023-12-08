package com.tzikin.pokeapp.data.model

import com.google.gson.annotations.SerializedName

data class PokemonTypeList(
    @SerializedName("name") val type: String,
    @SerializedName("url") val urlTypeInfo: String
)
