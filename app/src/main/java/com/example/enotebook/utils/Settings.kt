package com.example.enotebook.utils

import android.content.Context
import android.content.SharedPreferences

class Settings(context:Context) {
    companion object{
        const val IS_APP_FIRST_LAUNCH="isAppFirstLaunch"
        const val PASSWORD="password"
    }

    private val sharedPreferences: SharedPreferences=context.getSharedPreferences("PasswordSharePreference",Context.MODE_PRIVATE)

    fun setFirstLaunched(){
        sharedPreferences.edit().putBoolean(IS_APP_FIRST_LAUNCH,false).apply()
    }

    fun isAppFirstLaunched():Boolean= sharedPreferences.getBoolean(IS_APP_FIRST_LAUNCH,true)


    fun setPassword(number:String){
        sharedPreferences.edit().putString(PASSWORD,number).apply()
    }

    fun getPassword():String=sharedPreferences.getString(PASSWORD,"")?:""
}