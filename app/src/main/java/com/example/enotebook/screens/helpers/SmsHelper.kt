package com.example.enotebook.screens.helpers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import com.example.enotebook.screens.sms.SmsSentReceiver

@SuppressLint("StaticFieldLeak")
object SmsHelper {
    var text = ""
    var cnt = 0
    var context: Context? = null
    var numbers: List<String> = listOf()

    fun send(text: String) {
        Log.d("nomerler", numbers.toString())
        val sentPendingIntents = ArrayList<PendingIntent>()
        val deliveredPendingIntents = ArrayList<PendingIntent>()
        val sentPI = PendingIntent.getBroadcast(context, 0,
            Intent(context, SmsSentReceiver::class.java), 0)
        if (cnt < numbers.size)
            try {
                val sms = SmsManager.getDefault()
                val mSMSMessage: ArrayList<String> = sms.divideMessage(text)
                sentPendingIntents.add(sentPI)
                sms.sendMultipartTextMessage(numbers[cnt++], null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("oshibka", e.localizedMessage!!)
                Toast.makeText(context, "SMS sending failed...", Toast.LENGTH_SHORT).show()
            }
    }

}