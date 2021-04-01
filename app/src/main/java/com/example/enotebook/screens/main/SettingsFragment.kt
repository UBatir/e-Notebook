package com.example.enotebook.screens.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.MainActivity
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSettingsBinding
import com.example.enotebook.screens.auth.signIn.SignInViewModel
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.utils.SharedPreferences
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


class SettingsFragment:BaseFragment(R.layout.fragment_settings) {

    private lateinit var binding:FragmentSettingsBinding
    private lateinit var navController: NavController
    private val settings: SharedPreferences by inject()
    private lateinit var parentNavController: NavController
    private val viewModel: SignInViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentSettingsBinding.bind(view)
        binding=_binding
        binding.btnBack.onClick {
            navController.popBackStack()
        }
        binding.btnSignOut.onClick {
            val dialog = AlertDialog.Builder(requireContext())
            dialog.setTitle(getString(R.string.exit_from_account))
            dialog.setMessage(getString(R.string.message_exit))
            dialog.setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.signOut()
                if (requireParentFragment().requireActivity() is MainActivity) {
                    parentNavController = Navigation.findNavController(requireParentFragment().requireActivity() as MainActivity, R.id.nav_host_fragment)
                    val action = MainFragmentDirections.actionMainFragmentToAuthorizationFragment()
                    parentNavController.navigate(action)
                }
            }
            dialog.setNegativeButton(getString(R.string.no)) { _, _ ->
            }
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        val languages=resources.getStringArray(R.array.languages)
        val arrayAdapter=ArrayAdapter(requireContext(), R.layout.dropdown_item, languages)
        binding.autoCompleteTextView.setAdapter(arrayAdapter)
        binding.autoCompleteTextView.setOnItemClickListener { _: AdapterView<*>, _: View, i: Int, _: Long ->
            when(i){
                0 -> {
                    changeLanguage("kaa")
                }
                1 ->{
                    changeLanguage("ru")
                }
            }
        }
    }
    private fun changeLanguage(language:String){
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
