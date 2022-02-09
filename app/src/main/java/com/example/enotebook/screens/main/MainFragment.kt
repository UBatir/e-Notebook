package com.example.enotebook.screens.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentMainBinding
import com.example.enotebook.extentions.onClick
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainFragment : Fragment(R.layout.fragment_main) {

    private lateinit var binding: FragmentMainBinding
    lateinit var childNavController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMainBinding.bind(view)
        setUpBottomNav()
        setAppBarCorersRadius()
        childNavController = Navigation.findNavController(view.findViewById(R.id.main_host_fragment))
        binding.bnvMain.background = null
        binding.fabMain.onClick {
            val action=MainFragmentDirections.actionMainFragmentToAddCustomerFragment()
            findNavController().navigate(action)
        }
    }

    private fun setUpBottomNav(){
        val navController = Navigation.findNavController(requireActivity(), R.id.main_host_fragment)
        NavigationUI.setupWithNavController(binding.bnvMain, navController)
    }

    private fun setAppBarCorersRadius() {
        val radius = resources.getDimension(R.dimen.default_app_bar_radius)
        val bottomBarBackground = binding.babMain.background as MaterialShapeDrawable
        bottomBarBackground.shapeAppearanceModel =
                bottomBarBackground.shapeAppearanceModel
                        .toBuilder()
                        .setTopRightCorner(CornerFamily.ROUNDED,radius)
                        .setTopLeftCorner(CornerFamily.ROUNDED,radius)
                        .build()
    }
}