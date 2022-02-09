package com.example.enotebook.data.local

import android.content.Context
import android.content.SharedPreferences
import com.example.enotebook.extentions.BooleanPreference
import com.example.enotebook.extentions.IntPreference
import com.example.enotebook.extentions.StringPreference

class SharedPreferences(context:Context) {

    private val sharedPreferences: SharedPreferences =context.getSharedPreferences("SharePreference",Context.MODE_PRIVATE)

    var position:Int by IntPreference(sharedPreferences,1)
    var password:String by StringPreference(sharedPreferences,"")
    var language:String by StringPreference(sharedPreferences,"")
    var changeLanguage:Boolean by BooleanPreference(sharedPreferences,false)
}