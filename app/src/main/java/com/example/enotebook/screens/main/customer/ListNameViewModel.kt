package com.example.enotebook.screens.main.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.data.model.Customer
import com.example.enotebook.extentions.Resource
import com.example.enotebook.helper.AuthHelper
import com.example.enotebook.helper.FireStoreHelper

class ListNameViewModel(private val fireStoreHelper: FireStoreHelper,private val authHelper: AuthHelper):ViewModel() {

    private val _listContacts:MutableLiveData<Resource<MutableList<Customer>>> = MutableLiveData()
    val listContacts:LiveData<Resource<MutableList<Customer>>> get() = _listContacts

    fun getContacts(){
        _listContacts.value=Resource.loading()
        fireStoreHelper.getContacts({
            _listContacts.value= Resource.success(it as MutableList<Customer>)
        },{
            _listContacts.value=Resource.error(it)
        })
    }

    fun deleteContact(customer: Customer){
        fireStoreHelper.deleteContact(customer,{
            _listContacts.value=Resource.success(null)
        },{
            _listContacts.value=Resource.error(it)
        })
    }

    fun changeName(customer: Customer){
        fireStoreHelper.changeName(customer,{
            _listContacts.value=Resource.success(null)
        },{
            _listContacts.value=Resource.error(it)
        })
    }

    fun changeBalance(customer: Customer, sum:Long, comment: String){
        fireStoreHelper.changeBalance(customer,sum,comment,{
            _listContacts.value=Resource.success(null)
        },{
            _listContacts.value=Resource.error(it)
        })
    }

    fun signOut(){
        authHelper.signOut()
    }
}