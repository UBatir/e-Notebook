package com.example.enotebook.screens.helpers

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

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

    fun signInWithGoogle(account: GoogleSignInAccount,onSuccess: () -> Unit,onFailure: (message: String) -> Unit){
        val credential=GoogleAuthProvider.getCredential(account.idToken,null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener {
                onSuccess.invoke()
            }
            .addOnFailureListener {
                onFailure.invoke(it.localizedMessage)
            }
    }

    fun signOut() {
        auth.signOut()
    }
}