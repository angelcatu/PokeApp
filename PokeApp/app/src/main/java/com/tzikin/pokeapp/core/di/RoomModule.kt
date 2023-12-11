package com.tzikin.pokeapp.core.di

import android.content.Context
import androidx.room.Room
import com.tzikin.pokeapp.data.database.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    private const val POKEMON_DATABASE_NAME = "pokemon_database"

    @Singleton
    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PokemonDatabase::class.java, POKEMON_DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideQuoteDao(db: PokemonDatabase) = db.getPokemonDao()

    @Singleton
    @Provides
    fun providePokemonStatDao(db: PokemonDatabase) = db.getPokemonStat()
}