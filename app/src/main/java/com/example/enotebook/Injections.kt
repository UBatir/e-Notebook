package com.example.enotebook

import com.example.enotebook.screens.auth.signIn.SignInViewModel
import com.example.enotebook.screens.auth.signUp.SignUpViewModel
import com.example.enotebook.screens.firebase.AuthHelper
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(androidApplication().applicationContext.getString(R.string.default_web_client_id))
            .requestEmail()
            .build() }
}

val helperModule = module {
    single { AuthHelper(get()) }
}

val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }

}
val adapterModule = module {

}