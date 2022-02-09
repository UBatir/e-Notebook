package com.example.enotebook.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enotebook.MainActivity
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentPasswordBinding
import com.example.enotebook.data.local.SharedPreferences
import com.example.enotebook.extentions.scope
import com.example.enotebook.extentions.toastLN
import com.google.firebase.auth.FirebaseAuth
import com.hanks.passcodeview.PasscodeView.PasscodeViewListener
import org.koin.android.ext.android.inject
import java.util.*


class PasswordFragment:Fragment(R.layout.fragment_password) {
    private val auth: FirebaseAuth by inject()
    private val settings: SharedPreferences by inject()
    private val binding by viewBinding(FragmentPasswordBinding::bind)
    private var selectedLanguage = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        if (!settings.changeLanguage) showDialog()
        if (settings.password.isNotEmpty()) {
            passcodeView.setLocalPasscode(settings.password).listener =
                object : PasscodeViewListener {
                    override fun onFail() {
                        toastLN("Wrong!!")
                    }

                    override fun onSuccess(number: String) {
                        checkAccount()
                    }
                }
        } else {
            passcodeView.listener = object : PasscodeViewListener {
                override fun onFail() {
                    toastLN("Wrong!!")
                }

                override fun onSuccess(number: String) {
                    settings.password=number
                    checkAccount()
                }
            }
        }
    }

    fun checkAccount(){
        if(auth.currentUser==null){
            findNavController().navigate(R.id.action_passwordFragment_to_authorizationFragment)
        }
        else{
            findNavController().navigate(R.id.action_passwordFragment_to_mainFragment)
        }
    }

    private fun showDialog(){
        val dialogLanguages = AlertDialog.Builder(requireContext())
        dialogLanguages.setTitle(getString(R.string.change_language))
        val languages = arrayOf(
            getString(R.string.russian_language),
            getString(R.string.karakalpak_language),
            getString(R.string.uzbek_language)
        )
        dialogLanguages.setSingleChoiceItems(languages, settings.position) { _, i ->
            selectedLanguage = languages[i]
            settings.position=i
        }
        dialogLanguages.setPositiveButton("Ok") { _, _ ->
            settings.changeLanguage=true
            when (selectedLanguage) {
                getString(R.string.russian_language) -> {
                    settings.language="ru"
                    setLocale()
                }
                getString(R.string.karakalpak_language) -> {
                    settings.language="kaa"
                    setLocale()
                }
                getString(R.string.uzbek_language) -> {
                    settings.language="uz"
                    setLocale()
                }
            }
        }
        dialogLanguages.show()
    }

    private fun setLocale() {
        val refresh = Intent(
            requireContext(),
            MainActivity::class.java
        )
        refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        requireActivity().intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        requireActivity().intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(refresh)
    }
}