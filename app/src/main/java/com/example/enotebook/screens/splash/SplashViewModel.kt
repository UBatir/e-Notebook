package com.example.enotebook.screens.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.enotebook.extentions.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel(){

    private var mutable:MutableLiveData<Unit> = MutableLiveData()
    val liveData:LiveData<Unit> get() = mutable

    init {
        viewModelScope.launch {
            delay(2000)
            mutable.value=Unit
        }
    }
}