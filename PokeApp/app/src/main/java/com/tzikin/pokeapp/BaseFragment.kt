package com.tzikin.pokeapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import com.tzikin.pokeapp.presentation.IMainActivity

abstract class BaseFragment<T : ViewDataBinding> : Fragment() {

    protected lateinit var binding: T
    abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        binding.also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    fun navigateTo(id: NavDirections) {
        (requireActivity() as IMainActivity).navigateTo(id)
    }

    fun showProgressBar() {
        (requireActivity() as IMainActivity).showProgressBar()
    }

    fun hideProgressBar() {
        (requireActivity() as IMainActivity).hideProgressBar()
    }

    fun setToolbarTitle(value: String) {
        (requireActivity() as IMainActivity).setToolbarTitle(value)
    }

    fun hideToolbar(value: Boolean) {
        (requireActivity() as IMainActivity).hideToolbar(value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.unbind()
    }


}