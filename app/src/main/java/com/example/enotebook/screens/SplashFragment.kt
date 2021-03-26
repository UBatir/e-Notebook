package com.example.enotebook.screens

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.utils.SharedPreferences
import org.koin.android.ext.android.inject
import java.util.*

class SplashFragment : BaseFragment(R.layout.fragment_splash) {

    private lateinit var navController: NavController
    private val settings:SharedPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        setLocale(settings.getLanguage())
        Handler(Looper.getMainLooper()).postDelayed({
            val action=SplashFragmentDirections.actionSplashFragmentToPasswordFragment()
            navController.navigate(action)
        },2000)
    }

    private fun setLocale(localeCode:String) {
        val resources = resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        config.setLocale(Locale(localeCode.toLowerCase(Locale.getDefault())))
        resources.updateConfiguration(config, dm)
    }
}