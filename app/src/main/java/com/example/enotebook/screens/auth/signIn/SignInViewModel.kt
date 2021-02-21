package com.example.enotebook.screens.auth.signIn

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.helpers.AuthHelper

class SignInViewModel(private val authHelper: AuthHelper):ViewModel() {

    private var _signInResult:MutableLiveData<Resource<String>> = MutableLiveData()
    val signInResult:LiveData<Resource<String>> get() = _signInResult

    fun signIn(email:String,password:String){
        _signInResult.value= Resource.loading()
        authHelper.signIn(email,password,{
            _signInResult.value=Resource.success("success")
        },{
            _signInResult.value= Resource.error(it)
        })
    }
}