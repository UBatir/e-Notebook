package com.example.enotebook

import com.example.enotebook.screens.auth.signIn.SignInViewModel
import com.example.enotebook.screens.auth.signUp.SignUpViewModel
import com.example.enotebook.screens.helpers.AuthHelper
import com.example.enotebook.screens.helpers.FireStoreHelper
import com.example.enotebook.screens.helpers.SmsHelper
import com.example.enotebook.screens.main.customer.ListNameAdapter
import com.example.enotebook.screens.main.customer.ListNameViewModel
import com.example.enotebook.screens.main.customer.addCustomer.AddCustomerViewModel
import com.example.enotebook.screens.main.customer.count.PersonFragment
import com.example.enotebook.screens.main.customer.count.PersonViewModel
import com.example.enotebook.screens.main.history.HistoryAdapter
import com.example.enotebook.screens.main.history.HistoryViewModel
import com.example.enotebook.screens.sms.SmsListNameAdapter
import com.example.enotebook.screens.sms.SmsListNameViewModel
import com.example.enotebook.utils.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseMessaging.getInstance() }
    /*single {
        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(androidApplication().applicationContext.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
    }*/
    single { GoogleSignIn.getClient(androidApplication().applicationContext, get()) }
}

val helperModule = module {
    single { AuthHelper(get()) }
    single { FireStoreHelper(get(),get()) }
    single { SmsHelper }
    single { SharedPreferences(androidApplication().applicationContext) }
}

val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { AddCustomerViewModel(get()) }
    viewModel { ListNameViewModel(get()) }
    viewModel { SmsListNameViewModel(get()) }
    viewModel { HistoryViewModel(get ()) }
    viewModel { PersonViewModel(get())}

}
val adapterModule = module {
    single { ListNameAdapter() }
    single { HistoryAdapter() }
    single { SmsListNameAdapter() }
}