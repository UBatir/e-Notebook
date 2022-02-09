package com.example.enotebook.screens.auth.signUp

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dd.processbutton.iml.ActionProcessButton
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSignUpBinding
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.extentions.scope
import com.example.enotebook.extentions.toastLN
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel by viewModel<SignUpViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.scope {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
        btnSignUp.setMode(ActionProcessButton.Mode.ENDLESS)
        btnSignUp.onClick {
            val email = etLogin.text.toString()
            val password = etPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                btnSignUp.progress = 1
                viewModel.signUp(email, password)
            } else {
                if (email.isEmpty()) {
                    etLogin.error = getString(R.string.email_not_entered_error)
                }
                if (password.isEmpty()) {
                    etPassword.error = getString(R.string.enter_your_password_error)
                }
            }
        }
    }

    private fun setUpObservers() = binding.scope {
        viewModel.signUpResult.observe(viewLifecycleOwner, {
            when (it.status) {
                ResourceState.LOADING -> {
                    btnSignUp.isClickable = false
                    btnSignUp.isFocusable = false
                }
                ResourceState.SUCCESS -> {
                    btnSignUp.progress = 100
                    val action = SignUpFragmentDirections.actionRegistrationFragmentToMainFragment()
                    findNavController().navigate(action)
                }
                ResourceState.ERROR -> {
                    toastLN(it.message)
                    btnSignUp.progress = -1
                    btnSignUp.isClickable = true
                    btnSignUp.isFocusable = true
                }
            }
        })
    }
}