package com.example.enotebook.screens.main.customer.count


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.Customer
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.helpers.FireStoreHelper

class PersonViewModel(private val fireStoreHelper: FireStoreHelper): ViewModel() {

    private val _listPerson:MutableLiveData<Resource<List<Customer?>>> = MutableLiveData()
    val listPerson:LiveData<Resource<List<Customer?>>> get() = _listPerson

    fun getPerson(name:String){
        _listPerson.value=Resource.loading()
        fireStoreHelper.getPerson(name,{
            _listPerson.value=Resource.success(it)
        },{
            _listPerson.value=Resource.error(it)
        })
    }

    fun deleteContact(customer: Customer){
        fireStoreHelper.deleteContact(customer,{
            _listPerson.value=Resource.success(null)
        },{
            _listPerson.value=Resource.error(it)
        })
    }
}