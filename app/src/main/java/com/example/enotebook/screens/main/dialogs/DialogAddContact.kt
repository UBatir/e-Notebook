package com.example.enotebook.screens.main.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.widget.AdapterView
import android.widget.AutoCompleteTextView
import android.widget.SimpleAdapter
import com.example.enotebook.MainActivity
import com.example.enotebook.R
import com.example.enotebook.databinding.DialogAddContactBinding


class DialogAddContact(context: Context?) :Dialog(context!!){

    private lateinit var mPeopleList:ArrayList<Map<String, String>>
    private lateinit var mAdapter:SimpleAdapter
    private lateinit var mTxtPhoneNo: AutoCompleteTextView
    private lateinit var binding: DialogAddContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_add_contact)
        val _binding = DialogAddContactBinding.inflate(LayoutInflater.from(context))
        binding=_binding
        mPeopleList= ArrayList()
        //populatePeopleList(activity = MainActivity())
        /*mTxtPhoneNo=findViewById(R.id.actvName)
        mAdapter= SimpleAdapter(
                context, mPeopleList, R.layout.auto_complete_tv, arrayOf("Name"),
                intArrayOf(R.id.tvNameContact)
        )
        mTxtPhoneNo.setAdapter(mAdapter)


        mTxtPhoneNo.onItemClickListener =
                AdapterView.OnItemClickListener { av, _, index, _ ->
                    val map = av.getItemAtPosition(index) as Map<*, *>
                    val name = map["Name"]
                    mTxtPhoneNo.setText("$name")
                    mTxtPhoneNo.setSelection(mTxtPhoneNo.text!!.length)
                }*/
    }

    private fun populatePeopleList(activity: MainActivity) {
        mPeopleList.clear()
        val people: Cursor = context.contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI, null, null, null, null)!!
        while (people!!.moveToNext()) {
            val contactName: String = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
            val contactId: String = people.getString(people
                    .getColumnIndex(ContactsContract.Contacts._ID))
            val hasPhone: String = people
                    .getString(people
                            .getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
            if (hasPhone.toInt() > 0) {
                // You know have the number so now query it like this
                val phones: Cursor = activity.contentResolver.query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                                + " = " + contactId, null, null)!!
                while (phones.moveToNext()) {
                    // store numbers and display a dialog letting the user
                    // select which.
                    val phoneNumber: String = phones
                            .getString(phones
                                    .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                    val namePhoneType: MutableMap<String, String> = HashMap()
                    namePhoneType["Name"] = contactName
                    namePhoneType["Phone"] = phoneNumber
                    // Then add this map to the list.
                    mPeopleList.add(namePhoneType)
                }
                phones.close()
            }
        }
        people.close()
        activity.startManagingCursor(people)
    }

}
