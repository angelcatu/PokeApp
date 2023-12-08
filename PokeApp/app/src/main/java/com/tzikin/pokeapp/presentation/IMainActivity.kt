package com.tzikin.pokeapp.presentation

import androidx.navigation.NavDirections

interface IMainActivity {

    /**
     * Used to navigate between screens
     */
    fun navigateTo(id: NavDirections)

    /**
     * Used to show progress bar with api call
     */
    fun showProgressBar()

    /**
     * Used to hide progressBar when the api call is finished
     */
    fun hideProgressBar()
}