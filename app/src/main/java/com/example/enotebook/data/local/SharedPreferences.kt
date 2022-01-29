package com.example.enotebook.data.local

import android.content.Context
import android.content.SharedPreferences

class SharedPreferences(context:Context) {
    companion object{
        const val IS_APP_FIRST_LAUNCH="isAppFirstLaunch"
        const val PASSWORD="password"
        const val LANGUAGE = "currentLanguage"
    }

    private val sharedPreferences: SharedPreferences =context.getSharedPreferences("PasswordSharePreference",Context.MODE_PRIVATE)

    fun setFirstLaunched(){
        sharedPreferences.edit().putBoolean(IS_APP_FIRST_LAUNCH,false).apply()
    }

    fun isAppFirstLaunched():Boolean= sharedPreferences.getBoolean(IS_APP_FIRST_LAUNCH,true)


    fun setPassword(number:String){
        sharedPreferences.edit().putString(PASSWORD,number).apply()
    }

    fun getPassword():String=sharedPreferences.getString(PASSWORD,"")?:""


    fun setLanguage(language: String) {
        sharedPreferences.edit().putString(LANGUAGE, language).apply()
    }

    fun getLanguage() : String = sharedPreferences.getString(LANGUAGE, "") ?:"ru"

}