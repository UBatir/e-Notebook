package com.example.enotebook.screens.auth.signUp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.enotebook.screens.extentions.Resource
import com.example.enotebook.screens.firebase.AuthHelper

class SignUpViewModel(private val authHelper: AuthHelper):ViewModel() {

    private var _signUpResult:MutableLiveData<Resource<String>> = MutableLiveData()
    val signUpResult:LiveData<Resource<String>> get() = _signUpResult

    fun signUp(email:String,password:String){
        _signUpResult.value=Resource.loading()
        authHelper.signUp(email,password,{
            _signUpResult.value=Resource.success("success")
        },{
            _signUpResult.value=Resource.error(it)
        })
    }
}