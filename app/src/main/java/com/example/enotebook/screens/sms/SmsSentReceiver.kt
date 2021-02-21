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
                Toast.makeText(context, "SMS SENT ${SmsHelper.cnt}", Toast.LENGTH_SHORT).show()
            }
            SmsManager.RESULT_ERROR_GENERIC_FAILURE -> {
                Toast.makeText(
                        context,
                        "SMS generic failure ${SmsHelper.cnt}",
                        Toast.LENGTH_SHORT
                ).show()
                SmsHelper.send(SmsHelper.text)
            }

            SmsManager.RESULT_ERROR_NO_SERVICE -> {
                Toast.makeText(
                        context,
                        "SMS no service ${SmsHelper.cnt}",
                        Toast.LENGTH_SHORT
                )
                        .show()
                SmsHelper.send(SmsHelper.text)
            }
            SmsManager.RESULT_ERROR_NULL_PDU -> {
                Toast.makeText(
                        context,
                        "SMS null PDU ${SmsHelper.cnt}",
                        Toast.LENGTH_SHORT
                ).show()
                SmsHelper.send(SmsHelper.text)
            }
            else -> {}
        }
    }
}