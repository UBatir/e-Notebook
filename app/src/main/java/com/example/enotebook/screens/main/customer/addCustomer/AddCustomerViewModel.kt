package com.example.enotebook.screens.main.customer.addCustomer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.data.model.Customer
import com.example.enotebook.extentions.Resource
import com.example.enotebook.helper.FireStoreHelper
import java.util.*

class AddCustomerViewModel(private val fireStoreHelper: FireStoreHelper):ViewModel() {

    private val _contactSet:MutableLiveData<Resource<Customer>> = MutableLiveData()
    val contactSet:LiveData<Resource<Customer>> get() = _contactSet

    fun addContact(customer: Customer){
        _contactSet.value=Resource.loading()
        fireStoreHelper.addContact(customer,{
            _contactSet.value=Resource.success(null)
        },{
            _contactSet.value=Resource.error(it)
        })
    }
}