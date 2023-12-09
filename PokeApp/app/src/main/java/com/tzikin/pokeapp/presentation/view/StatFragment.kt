package com.tzikin.pokeapp.presentation.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tzikin.pokeapp.R
import com.tzikin.pokeapp.presentation.viewmodel.StatViewModel

class StatFragment : Fragment() {

    companion object {
        fun newInstance() = StatFragment()
    }

    private lateinit var viewModel: StatViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_stat, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StatViewModel::class.java)
        // TODO: Use the ViewModel
    }

}