package com.tzikin.pokeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import androidx.room.Insert
import com.tzikin.pokeapp.core.di.ConnectivityStatusModule
import com.tzikin.pokeapp.databinding.ActivityMainBinding
import com.tzikin.pokeapp.presentation.IMainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), IMainActivity {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    @Inject
    @ConnectivityStatusModule.MyConnectivityInternetStatus
    lateinit var connectivityStatus: Flow<Boolean>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_main, null, false)
        binding.also {
            it.lifecycleOwner = this
        }
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        verifyInternetConnectionStatus()

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun verifyInternetConnectionStatus() {
        lifecycleScope.launch {
            connectivityStatus.collect{isConnected ->
                when(isConnected){
                    true -> {
                        Toast.makeText(applicationContext, "Internet is connected", Toast.LENGTH_SHORT).show()
                    }
                    false -> {
                        Toast.makeText(applicationContext, "Internet is disconnected", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    override fun navigateTo(id: NavDirections) {
        navController.navigate(id)
    }

    override fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun setToolbarTitle(value: String) {
        binding.toolbar.title = value
    }

    override fun hideToolbar(value: Boolean) {
        if (value) binding.toolbar.visibility = View.GONE
        else binding.toolbar.visibility = View.VISIBLE
    }
}