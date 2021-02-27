package com.example.enotebook

import com.example.enotebook.screens.auth.signIn.SignInViewModel
import com.example.enotebook.screens.auth.signUp.SignUpViewModel
import com.example.enotebook.screens.helpers.AuthHelper
import com.example.enotebook.screens.helpers.FireStoreHelper
import com.example.enotebook.screens.helpers.SmsHelper
import com.example.enotebook.screens.main.customer.ListNameViewModel
import com.example.enotebook.screens.main.customer.addCustomer.AddCustomerViewModel
import com.example.enotebook.screens.main.history.HistoryViewModel
import com.example.enotebook.screens.sms.SmsListNameViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single { FirebaseMessaging.getInstance() }
}

val helperModule = module {
    single { AuthHelper(get()) }
    single { FireStoreHelper(get(),get()) }
    single { SmsHelper }
}

val viewModelModule = module {
    viewModel { SignInViewModel(get()) }
    viewModel { SignUpViewModel(get()) }
    viewModel { AddCustomerViewModel(get()) }
    viewModel { ListNameViewModel(get()) }
    viewModel { SmsListNameViewModel(get()) }
    viewModel { HistoryViewModel(get ()) }

}
val adapterModule = module {

}