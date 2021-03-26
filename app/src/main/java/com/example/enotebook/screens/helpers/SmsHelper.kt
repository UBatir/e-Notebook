package com.example.enotebook.screens.helpers

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.telephony.SmsManager
import android.widget.Toast
import com.example.enotebook.screens.sms.SmsSentReceiver

@SuppressLint("StaticFieldLeak")
object SmsHelper {
    var cnt = 0
    var context: Context? = null
    var name:MutableList<String> = mutableListOf()
    var numbers: MutableList<String> = mutableListOf()
    var sum: MutableList<String> = mutableListOf()
    var date: MutableList<String> = mutableListOf()


    fun send() {
        val sentPendingIntents = ArrayList<PendingIntent>()
        val deliveredPendingIntents = ArrayList<PendingIntent>()
        val sentPI = PendingIntent.getBroadcast(context, 0,
            Intent(context, SmsSentReceiver::class.java), 0)
                    try {
                val sms = SmsManager.getDefault()
                sentPendingIntents.add(sentPI)
                val mSMSMessage: ArrayList<String> = sms.divideMessage("F.I.O.- ${name[cnt]} \n Qariz muǵdarı: ${sum[cnt]} \n Assalawma aleykum! Erteńge(${date[cnt]}) tólew kerek bolǵan qarızıńızdı tólep ketiwdi umıtpań.")
                sms.sendMultipartTextMessage(numbers[cnt++], null, mSMSMessage,
                    sentPendingIntents, deliveredPendingIntents)
                if (cnt == numbers.size){
                    cnt = 0
                    numbers.clear()
                    name.clear()
                    sum.clear()
                    date.clear()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
    }
}