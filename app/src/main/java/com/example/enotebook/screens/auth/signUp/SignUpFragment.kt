package com.example.enotebook.screens.auth.signUp

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSignUpBinding
import com.example.enotebook.extentions.BaseFragment
import com.example.enotebook.extentions.ResourceState
import com.example.enotebook.extentions.onClick
import com.example.enotebook.extentions.scope
import org.koin.androidx.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private lateinit var navController: NavController
    private val binding by viewBinding(FragmentSignUpBinding::bind)
    private val viewModel:SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)=binding.scope {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        setUpObservers()
        btnSignUp.onClick {
            val email=etLogin.text.toString()
            val password=etPassword.text.toString()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                viewModel.signUp(email,password)
            }else{
                if(email.isEmpty()){
                    etLogin.error=getString(R.string.email_not_entered_error)
                }
                if(password.isEmpty()){
                    etPassword.error=getString(R.string.enter_your_password_error)
                }
            }
        }
    }

    private fun setUpObservers() =binding.scope{
        viewModel.signUpResult.observe(viewLifecycleOwner,{
            when(it.status){
                ResourceState.LOADING-> loading.visibility= View.VISIBLE
                ResourceState.SUCCESS->{
                    binding.loading.visibility=View.INVISIBLE
                    val action= SignUpFragmentDirections.actionRegistrationFragmentToMainFragment()
                    navController.navigate(action)
                }
                ResourceState.ERROR->{
                    toastLN(it.message)
                    binding.loading.visibility=View.INVISIBLE
                }
            }
        })
    }
}