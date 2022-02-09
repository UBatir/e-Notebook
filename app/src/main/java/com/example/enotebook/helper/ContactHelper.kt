package com.example.enotebook.helper

import android.app.Activity
import android.database.Cursor
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment

abstract class ContactHelper(viewResId: Int) : Fragment(viewResId) {

    private lateinit var storeContacts:ArrayList<Map<String,String>>

   fun getContactsIntoArrayList() : ArrayList<Map<String,String>> {
       storeContacts = ArrayList()
       val cursor: Cursor? =context?.contentResolver!!.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null)
        while (cursor!!.moveToNext()) {
            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val map:MutableMap<String,String> = mutableMapOf()
            map["Name"]=name
            map["Number"]=phoneNumber
            storeContacts.add(map)
        }
        cursor.close()
       return storeContacts
    }

    protected fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}