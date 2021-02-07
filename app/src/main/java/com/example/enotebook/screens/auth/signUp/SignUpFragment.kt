package com.example.enotebook.screens.auth.signUp

import android.os.Bundle
import android.view.View
import androidx.compose.ui.viewinterop.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSignUpBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import org.koin.android.viewmodel.ext.android.viewModel

class SignUpFragment : BaseFragment(R.layout.fragment_sign_up) {

    private lateinit var navController: NavController
    private lateinit var binding:FragmentSignUpBinding
    private val viewModel: SignUpViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val _binding=FragmentSignUpBinding.bind(view)
        binding=_binding
        navController=Navigation.findNavController(view)
        setUpObservers()
        with(binding){
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
    }

    private fun setUpObservers() {
        viewModel.signUpResult.observe(viewLifecycleOwner,{
            when(it.status){
                ResourceState.LOADING-> binding.loading.visibility= View.VISIBLE
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