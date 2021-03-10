package com.example.enotebook.screens.main.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.Customer
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.helpers.FireStoreHelper

class HistoryViewModel(private val fireStoreHelper: FireStoreHelper):ViewModel() {

    private val _listHistory:MutableLiveData<Resource<List<Customer?>>> = MutableLiveData()
    val listHistory:LiveData<Resource<List<Customer?>>> get() = _listHistory

    fun getHistory(id:String){
        _listHistory.value=Resource.loading()
        fireStoreHelper.getHistory(id,{
            _listHistory.value=Resource.success(it)
        },{
            _listHistory.value=Resource.error(it)
        })
    }
}