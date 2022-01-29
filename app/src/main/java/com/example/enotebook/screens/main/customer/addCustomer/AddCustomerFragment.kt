package com.example.enotebook.screens.main.customer.addCustomer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentAddContactBinding
import com.example.enotebook.extentions.BaseFragment
import com.example.enotebook.extentions.onClick
import com.google.android.material.tabs.TabLayoutMediator


class AddCustomerFragment:BaseFragment(R.layout.fragment_add_contact) {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentAddContactBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentAddContactBinding.bind(view)
        binding=_binding
        val fragmentList = arrayListOf<Fragment>(
            FragmentFull(),
            FragmentInstallment()
        )
        binding.btnBack.onClick {
            navController.popBackStack()
        }
        val adapter= ViewPagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)
        binding.viewPager.adapter=adapter
        val tabLayoutMediator =
            TabLayoutMediator(binding.tabs,binding.viewPager
            ) { tab, position ->
                when (position) {
                    0 -> tab.text = "Qarizg'a beriw"
                    1 -> tab.text = "Qarizg'a bolip beriw"
                }
            }
        tabLayoutMediator.attach()
    }
}
