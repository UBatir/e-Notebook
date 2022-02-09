package com.example.enotebook.screens.auth.signIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enotebook.R
import com.example.enotebook.data.local.SharedPreferences
import com.example.enotebook.databinding.FragmentSignInBinding
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.extentions.scope
import com.example.enotebook.extentions.toastLN
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)
    private val viewModel by viewModel<SignInViewModel>()
    private val mGoogleSignInClient: GoogleSignInClient by inject()

    companion object{
        private const val SIGN_IN:Int=1
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        btnLogin.onClick {
            val email = etLogin.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                btnLogin.progress = 1
                viewModel.signIn(email, password)
            } else {
                if (email.isEmpty()) {
                    etLogin.error = getString(R.string.email_not_entered_error)
                }
                if (password.isEmpty()) {
                    etPassword.error = getString(R.string.enter_your_password_error)
                }
            }
        }
        btnSignUp.onClick {
            val action =
                SignInFragmentDirections.actionAuthorizationFragmentToRegistrationFragment()
            findNavController().navigate(action)
        }
        googleLogin.onClick {
            signIn()
        }
    }

    private fun setUpObservers()=binding.scope {
        viewModel.signInResult.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> {
                    btnLogin.isClickable = false
                    btnLogin.isFocusable = false
                }
                ResourceState.SUCCESS -> {
                    btnLogin.progress = 100
                    val action =
                        SignInFragmentDirections.actionAuthorizationFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                ResourceState.ERROR -> {
                    toastLN(it.message)
                    btnLogin.progress = -1
                    btnLogin.isClickable = true
                    btnLogin.isFocusable = true
                }
            }
        })
    }

    private fun signIn(){
        val signInIntent  =mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account=task.getResult(ApiException::class.java)
                account?.let {
                    viewModel.signInWithGoogle(it)
                    setUpObserversGoogle()
                }
            }catch (e:ApiException){
                when(e.statusCode){
                    7->toastLN(getString(R.string.turnOn))
                }
                toastLN(e.localizedMessage)
            }
        }
    }

    private fun setUpObserversGoogle()=binding.scope {
        viewModel.signInGoogle.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.SUCCESS -> {
                    loading.hide()
                    val action =
                        SignInFragmentDirections.actionAuthorizationFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                ResourceState.ERROR -> {
                    loading.hide()
                    toastLN(it.message)
                }
                ResourceState.LOADING -> loading.show()
            }
        })
    }
}