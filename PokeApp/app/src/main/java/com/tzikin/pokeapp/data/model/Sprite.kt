package com.tzikin.pokeapp.data.model

import com.google.gson.annotations.SerializedName

data class Sprite(
    @SerializedName("front_default") val frontImageUrl: String,
    @SerializedName("front_shiny") val fronShinyImageUrl: String
)