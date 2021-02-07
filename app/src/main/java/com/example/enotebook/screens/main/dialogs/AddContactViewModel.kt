package com.example.enotebook.screens.main.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.firebase.FireStoreHelper
import io.grpc.internal.SharedResourceHolder

class AddContactViewModel(private val fireStoreHelper: FireStoreHelper):ViewModel() {

    private val _contactSet:MutableLiveData<Resource<String>> = MutableLiveData()
    val contactSet:LiveData<Resource<String>> get() = _contactSet

    fun addContact(name:String,sum:Long,comment:String,phoneNumber:String,data:String){
        _contactSet.value=Resource.loading()
        fireStoreHelper.addContact(name,sum,comment,phoneNumber,data,{
            _contactSet.value=Resource.success("success")
        },{
            _contactSet.value=Resource.error(it)
        })
    }
}