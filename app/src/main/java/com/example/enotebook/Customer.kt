package com.example.enotebook

data class Customer(
        var id:String="",
        var name:String="",
        var sum:Long=0,
        var comment:String="",
        var phoneNumber:String="",
        var getDate:Long=0,
        var setDate:String="",
        var changeDate:String="",
        var isChecked:Boolean=false
)