package com.example.enotebook.screens.main.customer.addCustomer

import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.SimpleAdapter
import android.widget.SimpleCursorAdapter
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentAddContactBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import org.koin.android.viewmodel.ext.android.viewModel


class AddCustomerFragment:BaseFragment(R.layout.fragment_add_contact) {

    private lateinit var mPeopleList: ArrayList<Map<String, String>>
    private lateinit var mAdapter: SimpleCursorAdapter
    private lateinit var txtName: AutoCompleteTextView
    private lateinit var txtPhone: AutoCompleteTextView
    private lateinit var binding: FragmentAddContactBinding
    private lateinit var navController: NavController
    private val viewModel: AddCustomerViewModel by viewModel()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentAddContactBinding.bind(view)
        binding=_binding
        txtName=binding.actvName
        txtPhone=binding.etPhoneNumber
        mPeopleList = ArrayList()
        populatePeopleList()
        mAdapter= SimpleCursorAdapter(
                context, R.layout.auto_complete_tv,mPeopleList, arrayOf("Name","Phone"),
                intArrayOf(R.id.tvNameContact)
        )
        txtName.setAdapter(mAdapter)
        binding.actvName.setSelection(binding.actvName.text!!.length)


        txtName.onItemClickListener =
                AdapterView.OnItemClickListener { av, _, index, _ ->
                    val map = av.getItemAtPosition(index) as Map<*, *>
                    val name = map["Name"]
                    val phone=map["Phone"]
                    txtName.setText("$name")
                    txtPhone.setText("$phone")
                    txtName.setSelection(txtName.text!!.length)
                }
        setUpObserves()
        with(binding){
            btnCancel.onClick {
                val action=AddCustomerFragmentDirections.actionAddContactFragmentToReportFragment2()
                navController.navigate(action)
            }
            btnPlus.onClick {
                val name:String=actvName.text.toString()
                val sum:Long=etSum.text.toString().toLong()
                val comment:String=etComment.text.toString()
                val phoneNumber:String=etPhoneNumber.text.toString()
                val getData:String=tvGetData.text.toString()
                val setData:String=tvSetData.text.toString()
                if(name.isNotEmpty()&&sum.toInt()!=null){
                    viewModel.addContact(name,sum,comment,phoneNumber,getData,setData)
                }else{
                    if(name.isEmpty()){
                        actvName.error=getString(R.string.enter_name)
                    }
                    if(sum.toInt()==null){
                        etSum.error=getString(R.string.enter_amount)
                    }
                }
            }
        }
    }

    private fun setUpObserves() {
        viewModel.contactSet.observe( viewLifecycleOwner,{
            when(it.status){
                ResourceState.LOADING->{

                }
                ResourceState.SUCCESS->{
                    toastLN("Added new person")
                    val action=AddCustomerFragmentDirections.actionAddContactFragmentToReportFragment2()
                    navController.navigate(action)
                }
                ResourceState.ERROR->{
                    toastLN(it.message)
                }
            }
        })
    }

    private fun populatePeopleList() {
        mPeopleList.clear()
        val people = context?.contentResolver!!.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null)!!
        while (people.moveToNext()) {
            val contactName = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val contactId = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts._ID))
            val phones = context?.contentResolver!!.query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId,
                    null, null)!!
            while (phones.moveToNext()) {
                val phoneNumber = phones.getString(
                        phones.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER))
                val namePhone: MutableMap<String, String> = HashMap()
                namePhone["Name"] = contactName
                namePhone["Phone"] = phoneNumber
                mPeopleList.add(namePhone)
            }
            phones.close()
        }
        people.close()
        activity?.startManagingCursor(people)
    }
}
