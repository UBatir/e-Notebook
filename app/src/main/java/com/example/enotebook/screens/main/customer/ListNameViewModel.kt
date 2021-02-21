package com.example.enotebook.screens.main.customer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.Customer
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.helpers.FireStoreHelper

class ListNameViewModel(private val fireStoreHelper: FireStoreHelper):ViewModel() {

    private val _listContacts:MutableLiveData<Resource<List<Customer?>>> = MutableLiveData()
    val listContacts:LiveData<Resource<List<Customer?>>> get() = _listContacts

    fun getContacts(){
        fireStoreHelper.getContacts({
            _listContacts.value= Resource.success(it)
        },{
            _listContacts.value=Resource.error(it)
        })
    }
}