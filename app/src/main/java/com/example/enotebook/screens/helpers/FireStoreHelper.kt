package com.example.enotebook.screens.helpers


import android.annotation.SuppressLint
import android.app.usage.UsageEvents
import com.example.enotebook.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import java.text.SimpleDateFormat
import java.util.*


class FireStoreHelper(private val auth: FirebaseAuth, private val db: FirebaseFirestore) {

    @SuppressLint("SimpleDateFormat")
    fun addContact(customer: Customer,
                   onSuccess: () -> Unit, onFailure: (msg: String?) -> Unit){
        val map:MutableMap<String, Any> = mutableMapOf()
        map["name"]=customer.name
        map["sum"]=customer.sum
        map["comment"]=customer.comment
        map["phoneNumber"]=customer.phoneNumber
        map["getData"]=customer.getData
        map["setData"]=customer.setData
        map["id"] = UUID.randomUUID().toString()
        map["changeData"]= SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Calendar.getInstance().time).toString()
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").document(map["id"].toString()).set(map)
        db.collection("contacts").document(auth.currentUser!!.uid).collection("history").document().set(map)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }

    fun getContacts(onSuccess: (list: List<Customer?>) -> Unit, onFailure: (msg: String) -> Unit){
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").get()
                .addOnSuccessListener { collection->
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

    fun getHistory(id: String, onSuccess: (list: List<Customer?>) -> Unit, onFailure: (msg: String) -> Unit){
        db.collection("contacts").document(auth.currentUser!!.uid).collection("history").whereEqualTo("id", id).get()
                .addOnSuccessListener { collection->
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

    fun getListName(onSuccess: (list: List<Customer>) -> Unit, onFailure: (msg: String) -> Unit){
        val time=System.currentTimeMillis()/1000+259200
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").whereLessThanOrEqualTo("getData", time).get()
                .addOnSuccessListener { collection->
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

    fun deleteContact(customer: Customer, onSuccess: () -> Unit, onFailure: (msg: String) -> Unit){
        db.collection("contacts").document(auth.currentUser!!.uid).collection("history").document().delete()
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").document(customer.id).delete()
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }

    fun changeName(customer: Customer, onSuccess: () -> Unit, onFailure: (msg: String) -> Unit){
        val update= hashMapOf<String, Any>(
                "name" to customer.name
        )
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").document(customer.id).update(update)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }

    @SuppressLint("SimpleDateFormat")
    fun changeBalance(customer: Customer, sum: Long, comment: String, onSuccess: () -> Unit, onFailure: (msg: String) -> Unit){
        val update= hashMapOf<String, Any>(
                "sum" to customer.sum
        )
        val addHistory=hashMapOf<String, Any>(
                "comment" to comment,
                "changeData" to SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault()).format(Calendar.getInstance().time).toString(),
                "phoneNumber" to customer.phoneNumber,
                "sum" to sum,
                "id" to customer.id
        )
        db.collection("contacts").document(auth.currentUser!!.uid).collection("data").document(customer.id).update(update)
        db.collection("contacts").document(auth.currentUser!!.uid).collection("history").document().set(addHistory)
                .addOnSuccessListener {
                    onSuccess.invoke()
                }
                .addOnFailureListener {
                    onFailure.invoke(it.localizedMessage)
                }
    }
}