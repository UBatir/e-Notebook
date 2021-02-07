package com.example.enotebook.screens.firebase

import com.google.firebase.auth.FirebaseAuth

class AuthHelper(private val auth:FirebaseAuth) {
    fun signUp(email: String, password: String,onSuccess:()->Unit,onFailure:(message:String)->Unit) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener{
                onFailure(it.localizedMessage)
            }
    }

    fun signIn(email: String,password: String,onSuccess: () -> Unit,onFailure: (message: String) -> Unit){
        auth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
            }
    }

}