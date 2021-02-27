package com.example.enotebook.screens.main.customer.dialogs

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.example.enotebook.databinding.DialogCalendarBinding
import com.example.enotebook.screens.extentions.onClick


class CalendarDialog(context: Context): Dialog(context) {

    private lateinit var binding:DialogCalendarBinding
    var getData:(time:String)->Unit={}
    fun getData(a:(time:String)->Unit){
        getData=a
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val _binding = DialogCalendarBinding.inflate(layoutInflater)
        setContentView(_binding.root)
        binding=_binding
        _binding.calendarView.setOnDateChangeListener{ _, year, month, dayOfMonth ->
            _binding.btnPositive.onClick {
                getData.invoke("$dayOfMonth.${month+1}.$year")
                dismiss()
            }
        }
        _binding.btnNegative.onClick {
            dismiss()
        }
    }
}