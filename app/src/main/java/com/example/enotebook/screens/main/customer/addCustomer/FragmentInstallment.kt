package com.example.enotebook.screens.main.customer.addCustomer

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentInstallmentBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.screens.main.customer.dialogs.CalendarDialog
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FragmentInstallment:BaseFragment(R.layout.fragment_installment) {

    private lateinit var binding:FragmentInstallmentBinding
    private val viewModel:AddCustomerViewModel by viewModel()
    var customer= Customer()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding=FragmentInstallmentBinding.bind(view)
        binding=_binding
        setUpObserves()
        with(binding){
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            tvSetData.text=sdf.format(Calendar.getInstance().time).toString()
            btnPlus.onClick {
                if(actvName.text.isNotEmpty()&&etSum.text!!.isNotEmpty()){
                    val n=etCountMonths.text.toString().toInt()
                    customer.name=actvName.text.toString()
                    customer.sum=etSum.text.toString().toLong()
                    customer.comment=etComment.text.toString()
                    customer.phoneNumber=etPhoneNumber.text.toString()
                    customer.setData=tvSetData.text.toString()
                    val date = sdf.parse(tvSetData.text.toString())
                    viewModel.addContact(customer)
                    for (i in 1 .. n){
                        customer.sum=etSum.text.toString().toLong()/n
                        customer.getData = (date.time)/1000+i*86400
                        //viewModel.addInstallment(customer)
                    }
                }else{
                    if(actvName.text.isEmpty()){
                        actvName.error=getString(R.string.enter_name)
                    }
                    if(etSum.text.isNullOrEmpty()){
                        etSum.error=getString(R.string.enter_amount)
                    }
                    if(etPhoneNumber.text.isNullOrEmpty()){
                        etPhoneNumber.error=getString(R.string.enter_number)
                    }
                }
            }
            tvSetData.onClick {
                val dialog= CalendarDialog(requireContext())
                dialog.getData {
                    tvSetData.text= it
                }
                dialog.show()
            }
        }
    }

    private fun setUpObserves() {
        viewModel.contactSet.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> {

                }
                ResourceState.SUCCESS -> {
                    toastLN("Added new person")
                    val action = AddCustomerFragmentDirections.actionAddCustomerFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                ResourceState.ERROR -> {
                    toastLN(it.message)
                }
            }
        })
    }
}