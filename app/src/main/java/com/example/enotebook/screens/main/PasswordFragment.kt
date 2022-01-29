package com.example.enotebook.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentPasswordBinding
import com.example.enotebook.extentions.BaseFragment
import com.example.enotebook.data.local.SharedPreferences
import com.google.firebase.auth.FirebaseAuth
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import org.koin.android.ext.android.inject


class PasswordFragment:BaseFragment(R.layout.fragment_password) {
    private lateinit var binding: FragmentPasswordBinding
    private lateinit var navController: NavController
    private val sharedPreferences: SharedPreferences by inject()
    private val auth: FirebaseAuth by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding = FragmentPasswordBinding.bind(view)
        binding = _binding
        navController = Navigation.findNavController(view)
        if (!sharedPreferences.isAppFirstLaunched()) {
            binding.passcodeView.setLocalPasscode(sharedPreferences.getPassword()).listener =
                object : PasscodeViewListener {
                    override fun onFail() {
                        Toast.makeText(requireContext(), "Wrong!!", Toast.LENGTH_SHORT).show()
                    }

                    override fun onSuccess(number: String) {
                        checkAccount()
                    }
                }
        } else {
            binding.passcodeView.listener = object : PasscodeViewListener {
                override fun onFail() {
                    Toast.makeText(requireContext(), "Wrong!!", Toast.LENGTH_SHORT).show()
                }

                override fun onSuccess(number: String) {
                    sharedPreferences.setPassword(number)
                    checkAccount()
                }
            }
        }
    }

    fun checkAccount(){
        if(auth.currentUser==null){
            val action= PasswordFragmentDirections.actionPasswordFragmentToAuthorizationFragment()
            navController.navigate(action)
        }
        else{
            val action= PasswordFragmentDirections.actionPasswordFragmentToMainFragment()
            navController.navigate(action)
        }
    }
}