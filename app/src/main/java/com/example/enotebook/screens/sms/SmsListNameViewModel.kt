package com.example.enotebook.screens.sms

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.data.model.Customer
import com.example.enotebook.extentions.Resource
import com.example.enotebook.helper.FireStoreHelper
import com.example.enotebook.helper.SmsHelper

class SmsListNameViewModel(private val fireStoreHelper: FireStoreHelper):ViewModel() {

    private val _smsListName:MutableLiveData<Resource<List<Customer>>> = MutableLiveData()
    val smsListName:LiveData<Resource<List<Customer>>> get() = _smsListName

    fun getListName(){
        _smsListName.value=Resource.loading()
        fireStoreHelper.getListName({
            _smsListName.value = Resource.success(it)
        }, {
            _smsListName.value = Resource.error(it)
        })
    }

    fun sendSms(
            name: List<String>,
            numbers: List<String>,
            sum: List<String>,
            date: List<String>,
            context: Context
    ) {
        SmsHelper.name= name as MutableList<String>
        SmsHelper.numbers = numbers as MutableList<String>
        SmsHelper.sum = sum as MutableList<String>
        SmsHelper.date = date as MutableList<String>
        SmsHelper.context = context
        SmsHelper.send()
    }
}