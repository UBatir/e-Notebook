package com.example.enotebook.screens.sms

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSmsListNameBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class SmsListNameFragment:BaseFragment(R.layout.fragment_sms_list_name) {

    private lateinit var binding: FragmentSmsListNameBinding
    private lateinit var navController: NavController
    private val viewModel:SmsListNameViewModel by viewModel()
    private val mAdapter=SmsListNameAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentSmsListNameBinding.bind(view)
        binding=_binding
        setUpObservers()
        viewModel.getListName()
        binding.recyclerViewListName.adapter=mAdapter
        binding.btnSendSms.onClick {
            val text=binding.etSendSms.text.toString()
            if(text.isNotEmpty()){
                viewModel.sendSms(text,mAdapter.models.filter { it.isChecked }.map { it.phoneNumber },requireContext())
            Log.d("abc", mAdapter.models.filter { it.isChecked }.toString())
            }else{
                binding.etSendSms.error=getString(R.string.enter_the_field)
            }
        }
    }

    private fun setUpObservers() {
        viewModel.smsListName.observe(viewLifecycleOwner,{
            when(it.status){
                ResourceState.LOADING->binding.progressBar.visibility=View.VISIBLE
                ResourceState.SUCCESS->{
                    binding.progressBar.visibility=View.GONE
                        mAdapter.models= it.data!!

                }
                ResourceState.ERROR->{
                    binding.progressBar.visibility=View.GONE
                    toastLN(it.message)
                }
            }
        })
    }
}