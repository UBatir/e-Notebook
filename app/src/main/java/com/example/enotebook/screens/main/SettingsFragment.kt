package com.example.enotebook.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.MainActivity
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSettingsBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.utils.SharedPreferences
import org.koin.android.ext.android.inject
import java.util.*


class SettingsFragment:BaseFragment(R.layout.fragment_settings) {

    private lateinit var binding:FragmentSettingsBinding
    private lateinit var navController: NavController
    private val settings: SharedPreferences by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentSettingsBinding.bind(view)
        binding=_binding
        binding.btnBack.onClick {
            navController.popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        var language=""
        val languages=resources.getStringArray(R.array.languages)
        val arrayAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.setOnItemClickListener { _: AdapterView<*>, _: View, i: Int, _: Long ->
            when(i){
                0->language="kaa"
                1->language="ru"
            }
        }
        binding.btnSave.onClick {
            if (language == "ru"){
                settings.setLanguage(language)
            }else if(language=="kaa"){
                settings.setLanguage(language)
            }
            val refresh = Intent(requireContext(), MainActivity::class.java)
            refresh.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(refresh)
        }
    }
}
