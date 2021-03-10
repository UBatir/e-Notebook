package com.example.enotebook.screens.sms

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.widget.Toast
import com.example.enotebook.screens.helpers.SmsHelper

class SmsSentReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when (resultCode) {
            Activity.RESULT_OK -> {
                SmsHelper.send(SmsHelper.text)
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }
            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                SmsHelper.send(SmsHelper.text)
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }

            SmsManager.RESULT_ERROR_NO_SERVICE -> {
                SmsHelper.send(SmsHelper.text)
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }
            SmsManager.RESULT_ERROR_NULL_PDU -> {
                SmsHelper.send(SmsHelper.text)
                Toast.makeText(context, "Sms ketti", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }
}