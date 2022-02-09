package com.example.enotebook.data.model

data class Customer(
        var id:String="",
        var name:String="",
        var sum:Long=0,
        var historySum:List<Long> = listOf(),
        var comment:String="",
        var phoneNumber:String="",
        var createDate:Long=0,
        var changeDate:String="",
        var isChecked:Boolean=false
)