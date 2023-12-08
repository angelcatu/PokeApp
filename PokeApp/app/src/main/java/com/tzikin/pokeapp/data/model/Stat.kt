package com.tzikin.pokeapp.data.model

import com.google.gson.annotations.SerializedName

data class Stat(
    @SerializedName("name") val statName: String,
    @SerializedName("ulr") val urlStat: String
)