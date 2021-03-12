package com.example.enotebook.screens.main.customer.addCustomer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentFullBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.screens.main.customer.dialogs.CalendarDialog
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class FragmentFull:BaseFragment(R.layout.fragment_full){
    private lateinit var binding: FragmentFullBinding
    private val viewModel: AddCustomerViewModel by viewModel()
    var customer= Customer()

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding= FragmentFullBinding.bind(view)
        binding=_binding
        setUpObserves()
        with(binding){
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            tvSetDate.text=sdf.format(Calendar.getInstance().time).toString()
            tvGetDate.text=sdf.format(Calendar.getInstance().time).toString()
            btnPlus.onClick {
                if(actvName.text.isNotEmpty()&&etSum.text!!.isNotEmpty()){
                    customer.name=actvName.text.toString()
                    customer.sum=etSum.text.toString().toLong()
                    customer.comment=etComment.text.toString()
                    customer.phoneNumber=etPhoneNumber.text.toString()
                    customer.setDate=tvSetDate.text.toString()

                    val date = sdf.parse(tvGetDate.text.toString())
                    customer.getDate = (date.time)/1000

                    viewModel.addContact(customer)
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
            tvSetDate.onClick {
                val dialog= CalendarDialog(requireContext())
                dialog.getData {
                    tvSetDate.text= it
                }
                dialog.show()
            }
            tvGetDate.onClick {
                val dialog= CalendarDialog(requireContext())
                dialog.getData {
                    tvGetDate.text=it
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
                    toastLN("Kontakt qosildi!")
                    val action =
                            AddCustomerFragmentDirections.actionAddCustomerFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                ResourceState.ERROR -> {
                    toastLN(it.message)
                }
            }
        })
    }

}