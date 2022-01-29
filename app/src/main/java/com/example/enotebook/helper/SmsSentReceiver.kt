package com.example.enotebook.helper

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.widget.Toast
import com.example.enotebook.helper.SmsHelper

class SmsSentReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                SmsHelper.send()
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }
            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                SmsHelper.send()
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }

            SmsManager.RESULT_ERROR_NO_SERVICE -> {
                SmsHelper.send()
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }
            SmsManager.RESULT_ERROR_NULL_PDU -> {
                SmsHelper.send()
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}