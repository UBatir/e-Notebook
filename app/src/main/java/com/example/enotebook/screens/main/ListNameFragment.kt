package com.example.enotebook.screens.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentListNameBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.screens.main.dialogs.DialogAddContact

class ListNameFragment : BaseFragment(R.layout.fragment_list_name) {

    private lateinit var binding: FragmentListNameBinding
    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentListNameBinding.bind(view)
        binding=_binding
        with(binding){
            fab.onClick {
                val dialog=DialogAddContact(context)
                dialog.show()
                Toast.makeText(context,"Istep tur",Toast.LENGTH_LONG).show()
            }
        }
    }
}