package com.example.enotebook.screens.main.customer.addCustomer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentAddContactBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.screens.main.customer.dialogs.CalendarDialog
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class AddCustomerFragment:BaseFragment(R.layout.fragment_add_contact) {

    private lateinit var binding: FragmentAddContactBinding
    private lateinit var navController: NavController
    private val viewModel: AddCustomerViewModel by viewModel()
    var customer=Customer()

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentAddContactBinding.bind(view)
        binding=_binding
        setUpObserves()
        with(binding){
            btnBack.onClick {
                navController.popBackStack()
            }
            val sdf = SimpleDateFormat("dd.MM.yyyy")
            tvSetData.text=sdf.format(Calendar.getInstance().time).toString()
            tvGetData.text=sdf.format(Calendar.getInstance().time).toString()
            btnPlus.onClick {
                if(actvName.text.isNotEmpty()&&etSum.text!!.isNotEmpty()){
                    customer.name=actvName.text.toString()
                    customer.sum=etSum.text.toString().toLong()
                    customer.comment=etComment.text.toString()
                    customer.phoneNumber=etPhoneNumber.text.toString()
                    customer.setData=tvSetData.text.toString()

                    val date = sdf.parse(tvGetData.text.toString())
                    customer.getData = (date.time)/1000

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
            tvSetData.onClick {
                val dialog= CalendarDialog(requireContext())
                dialog.getData {
                    tvSetData.text= it
                }
                dialog.show()
            }
            tvGetData.onClick {
                val dialog= CalendarDialog(requireContext())
                dialog.getData {
                    tvGetData.text=it
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
                    val action =
                            AddCustomerFragmentDirections.actionAddCustomerFragmentToMainFragment()
                    navController.navigate(action)
                }
                ResourceState.ERROR -> {
                    toastLN(it.message)
                }
            }
        })
    }

}
