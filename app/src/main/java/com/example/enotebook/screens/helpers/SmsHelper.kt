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
    var text=""
    var cnt = 0
    var context: Context? = null
    var numbers: MutableList<String> = mutableListOf()
    var sum: MutableList<String> = mutableListOf()
    var data: MutableList<String> = mutableListOf()


    fun send(text:String) {
        val sentPendingIntents = ArrayList<PendingIntent>()
        val deliveredPendingIntents = ArrayList<PendingIntent>()
        val sentPI = PendingIntent.getBroadcast(context, 0,
            Intent(context, SmsSentReceiver::class.java), 0)
                    try {
                val sms = SmsManager.getDefault()
                sentPendingIntents.add(sentPI)
                val mSMSMessage: ArrayList<String> = sms.divideMessage("$text \n Qariz mug'dari: ${sum[cnt]}  \n Kelisilgen waqit: ${data[cnt]}")
                sms.sendMultipartTextMessage(numbers[cnt++], null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents)
                if (cnt == numbers.size){
                    cnt = -1
                    numbers.clear()
                    sum.clear()
                    data.clear()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }
}