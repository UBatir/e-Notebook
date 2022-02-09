package com.example.enotebook.screens.main.customer.addCustomer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enotebook.R
import com.example.enotebook.data.model.Customer
import com.example.enotebook.databinding.FragmentAddCustomerBinding
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.extentions.scope
import com.example.enotebook.extentions.toastLN
import com.example.enotebook.helper.ContactHelper
import com.example.enotebook.screens.main.customer.dialogs.CalendarDialog
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*


class AddCustomerFragment : ContactHelper(R.layout.fragment_add_customer) {
    private val binding by viewBinding(FragmentAddCustomerBinding::bind)
    private val viewModel: AddCustomerViewModel by viewModel()
    private lateinit var mAdapter: SimpleAdapter
    private var customer = Customer()

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        setUpObserves()
        mAdapter = SimpleAdapter(
            context, getContactsIntoArrayList(), R.layout.auto_complete_tv, arrayOf("Name"),
            intArrayOf(R.id.tvNameContact)
        )
        actvName.setAdapter(mAdapter)
        actvName.setSelection(actvName.text!!.length)
        actvName.onItemClickListener =
            AdapterView.OnItemClickListener { av, _, index, _ ->
                val map = av.getItemAtPosition(index) as Map<*, *>
                val name = map["Name"]
                val phoneNumber = map["Number"]
                actvName.setText("$name")
                val l = phoneNumber.toString().length
                etPhoneNumber.setText(phoneNumber.toString().substring(l - 9, l))
                actvName.setSelection(actvName.text!!.length)
            }

        val sdf = SimpleDateFormat("dd.MM.yyyy")
        tvCreateDate.text = sdf.format(Calendar.getInstance().time).toString()
        btnAdd.onClick {
            if (actvName.text.isNotEmpty() && etSum.text!!.isNotEmpty()) {
                customer.name = actvName.text.toString()
                customer.sum = etSum.text.toString().toLong()
                customer.comment = etComment.text.toString()
                customer.phoneNumber = etPhoneNumber.text.toString()
                val date = sdf.parse(tvCreateDate.text.toString())
                customer.createDate = (date.time) / 1000
                viewModel.addContact(customer)
            } else {
                if (actvName.text.isEmpty()) {
                    actvName.error = getString(R.string.enter_name)
                }
                if (etSum.text.isNullOrEmpty()) {
                    etSum.error = getString(R.string.enter_amount)
                }
                if (etPhoneNumber.text.isNullOrEmpty()) {
                    etPhoneNumber.error = getString(R.string.enter_number)
                }
            }
        }
        tvCreateDate.onClick {
            val dialog = CalendarDialog(requireContext())
            dialog.getData {
                tvCreateDate.text = it
            }
            dialog.show()
        }
    }

    private fun setUpObserves() {
        viewModel.contactSet.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> {}
                ResourceState.SUCCESS -> {
                    findNavController().popBackStack()
                }
                ResourceState.ERROR -> {
                    toastLN(it.message)
                }
            }
        })
    }

}