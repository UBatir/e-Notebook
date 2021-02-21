package com.example.enotebook.screens.helpers


import com.example.enotebook.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore


class FireStoreHelper(private val auth:FirebaseAuth,private val db:FirebaseFirestore) {

    fun addContact(name:String,sum:Long,comment:String,phoneNumber:String,getData:Long,setData:String,
                   onSuccess:()->Unit,onFailure:(msg:String?)->Unit){
        val map:MutableMap<String,Any> = mutableMapOf()
        map["name"]=name
        map["sum"]=sum
        map["comment"]=comment
        map["phoneNumber"]=phoneNumber
        map["getData"]=getData
        map["setData"]=setData
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").document().set(map)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }

    fun getContacts(onSuccess: (list:List<Customer?>) -> Unit, onFailure:(msg:String)->Unit){
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").get()
                .addOnSuccessListener {collection->
                    if(collection.documents.isNotEmpty()){
                        onSuccess.invoke(collection.documents.map {
                            it.toObject(Customer::class.java)
                        })
                    }else{
                        onSuccess.invoke(listOf())
                    }
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }

    fun getListName(onSuccess: (list:List<Customer>) -> Unit,onFailure: (msg: String) -> Unit){
        val time=System.currentTimeMillis()/1000+259200
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").whereLessThanOrEqualTo("getData",time).get()
                .addOnSuccessListener {collection->
                    if(collection.documents.isNotEmpty()){
                        onSuccess.invoke(collection.documents.map {
                            it.toObject(Customer::class.java)!!
                        })
                    }else{
                        onSuccess.invoke(listOf())
                    }
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }
}