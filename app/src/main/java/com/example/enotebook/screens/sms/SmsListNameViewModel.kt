package com.example.enotebook.screens.sms

import android.content.Context
import android.text.format.DateFormat.format
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.Customer
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.helpers.FireStoreHelper
import com.example.enotebook.screens.helpers.SmsHelper
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

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
        text: String,
        numbers: List<String>,
        sum: List<String>,
        data: List<String>,
        context: Context
    ) {
        SmsHelper.text = text
        SmsHelper.numbers = numbers as MutableList<String>
        SmsHelper.sum = sum as MutableList<String>
        SmsHelper.data = data as MutableList<String>
        SmsHelper.context = context
        SmsHelper.send(text)
    }
}