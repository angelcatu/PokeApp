package com.tzikin.pokeapp.presentation.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.tzikin.pokeapp.presentation.view.DetailFragment
import com.tzikin.pokeapp.presentation.view.StatFragment

class PokemonInformationAdapter(
    fragment: Fragment,
    private val numPages: Int,
    val id: Long
) :
    FragmentStateAdapter(fragment) {

    companion object {
        const val detail = 0
        const val stat = 1
    }

    override fun getItemCount(): Int = numPages

    override fun createFragment(position: Int): Fragment {
        val fragment: Fragment
        when (position) {
            detail -> {
                fragment = DetailFragment()
            }

            stat -> {
                fragment = StatFragment()

            }

            else -> {
                fragment = DetailFragment()
            }
        }
        val bundle = Bundle()
        bundle.putLong("pokeID", id)
        fragment.arguments = bundle

        return fragment
    }
}