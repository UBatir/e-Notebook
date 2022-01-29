package com.example.enotebook.screens.main.customer.addCustomer

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView
import android.widget.SimpleAdapter
import androidx.navigation.fragment.findNavController
import com.example.enotebook.data.model.Customer
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentFullBinding
import com.example.enotebook.extentions.BaseFragment
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.screens.main.customer.dialogs.CalendarDialog
import org.koin.android.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class FragmentFull:BaseFragment(R.layout.fragment_full){
    private lateinit var binding: FragmentFullBinding
    private val viewModel: AddCustomerViewModel by viewModel()
    var customer= Customer()
    private lateinit var mAdapter: SimpleAdapter
    private lateinit var storeContacts:ArrayList<Map<String,String>>

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding= FragmentFullBinding.bind(view)
        binding=_binding
        setUpObserves()
        storeContacts= ArrayList()
        getContactsIntoArrayList()
        mAdapter= SimpleAdapter(
                context, storeContacts, R.layout.auto_complete_tv, arrayOf("Name"),
                intArrayOf(R.id.tvNameContact)
        )
        binding.actvName.setAdapter(mAdapter)
        binding.actvName.setSelection(binding.actvName.text!!.length)


        binding.actvName.onItemClickListener =
                AdapterView.OnItemClickListener { av, _, index, _ ->
                    val map = av.getItemAtPosition(index) as Map<*, *>
                    val name = map["Name"]
                    val phoneNumber=map["Number"]
                    binding.actvName.setText("$name")
                    binding.etPhoneNumber.setText("$phoneNumber")
                    binding.actvName.setSelection(binding.actvName.text!!.length)
                }

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
                    customer.getDate=(date.time)/1000

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

    private fun getContactsIntoArrayList() {
        val cursor: Cursor? =requireActivity().contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (cursor!!.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val map:MutableMap<String,String> = mutableMapOf()
            map["Name"]=name
            map["Number"]=phoneNumber
            storeContacts.add(map)
        }
        cursor.close()
    }

    private fun setUpObserves() {
        viewModel.contactSet.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> {

                }
                ResourceState.SUCCESS -> {
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