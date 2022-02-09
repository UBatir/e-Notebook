package com.example.enotebook.screens.splash

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.enotebook.R
import com.example.enotebook.data.local.SharedPreferences
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModel<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner,{
            val action=SplashFragmentDirections.actionSplashFragmentToPasswordFragment()
            findNavController().navigate(action)
        })
    }
}