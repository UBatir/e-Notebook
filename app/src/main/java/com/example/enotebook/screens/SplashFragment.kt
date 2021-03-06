package com.example.enotebook.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.screens.extentions.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.ext.android.inject

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private lateinit var navController: NavController
    private val auth: FirebaseAuth by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        Handler(Looper.getMainLooper()).postDelayed({
            if(auth.currentUser==null){
                val action=SplashFragmentDirections.actionSplashFragmentToAuthorizationFragment()
                navController.navigate(action)
            }
            else{
                val action=SplashFragmentDirections.actionSplashFragmentToMainFragment()
                navController.navigate(action)

            }
        },2000)
    }
}