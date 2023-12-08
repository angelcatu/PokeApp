package com.tzikin.pokeapp.presentation.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.tzikin.pokeapp.BaseFragment
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.databinding.FragmentFrontBinding
import com.tzikin.pokeapp.presentation.viewmodel.FrontViewModel

class FrontFragment : BaseFragment<FragmentFrontBinding>() {

    private val viewModel: FrontViewModel by viewModels()
    override val layoutId: Int
        get() = R.layout.fragment_front

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}