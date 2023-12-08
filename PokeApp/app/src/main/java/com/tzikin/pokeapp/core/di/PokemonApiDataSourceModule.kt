package com.tzikin.pokeapp.core.di

import com.google.gson.GsonBuilder
import com.tzikin.pokeapp.Constants
import com.tzikin.pokeapp.data.network.PokemonApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object PokemonApiDataSourceModule {

    @Singleton
    @Provides
    fun providesPokemonAccessApi(): PokemonApi {
        val builder = OkHttpClient.Builder()
        builder
            .connectTimeout(Constants.API_RESTFUL_CONNECT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(Constants.API_RESTFUL_WRITE_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(Constants.API_RESTFUL_READ_TIME_OUT_SECONDS, TimeUnit.SECONDS)
            .build()
        return Retrofit.Builder()
            .client(builder.build())
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(PokemonApi::class.java)
    }
}