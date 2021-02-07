package com.example.enotebook.screens.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class FireStoreHelper(private val auth:FirebaseAuth,private val db:FirebaseFirestore) {

    fun addContact(name:String,sum:Long,comment:String,phoneNumber:String,data:String,
                   onSuccess:()->Unit,onFailure:(msg:String?)->Unit){
        val map:MutableMap<String,Any> = mutableMapOf()
        map["name"]=name
        map["sum"]=sum
        map["comment"]=comment
        map["phoneNumber"]=phoneNumber
        map["data"]=data
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").document().set(map)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }
}