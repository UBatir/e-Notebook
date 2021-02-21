package com.example.enotebook.screens.sms

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.Customer
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.helpers.FireStoreHelper
import com.example.enotebook.screens.helpers.SmsHelper

class SmsListNameViewModel(private val fireStoreHelper: FireStoreHelper):ViewModel() {

    private val _smsListName:MutableLiveData<Resource<List<Customer>>> = MutableLiveData()
    val smsListName:LiveData<Resource<List<Customer>>> get() = _smsListName

    fun getListName(){
        fireStoreHelper.getListName({
            _smsListName.value=Resource.success(it)
        },{
            _smsListName.value=Resource.error(it)
        })
    }

    fun sendSms(text: String, numbers: List<String>, context: Context) {
        SmsHelper.text = text
        SmsHelper.numbers = numbers
        SmsHelper.context = context
        SmsHelper.send(text)
    }
}