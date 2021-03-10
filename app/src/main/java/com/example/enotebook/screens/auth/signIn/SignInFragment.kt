package com.example.enotebook.screens.auth.signIn

import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSignInBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.utils.Settings
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in){

    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModel()
    private lateinit var settings: Settings

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentSignInBinding.bind(view)
        settings= Settings(requireContext())
        settings.setFirstLaunched()
        binding=_binding
        setUpObservers()
        with(binding){
            btnLogin.onClick {
                val email=etLogin.text.toString()
                val password=etPassword.text.toString()
                if(email.isNotEmpty()&&password.isNotEmpty()){
                    viewModel.signIn(email,password)
                }else{
                    if (email.isEmpty()){
                        etLogin.error=getString(R.string.email_not_entered_error)
                    }
                    if(password.isEmpty()){
                        etPassword.error=getString(R.string.enter_your_password_error)
                    }
                }
            }
            btnSignUp.onClick {
                val action= SignInFragmentDirections.actionAuthorizationFragmentToRegistrationFragment()
                navController.navigate(action)
            }
        }
    }

    private fun setUpObservers(){
        viewModel.signInResult.observe(viewLifecycleOwner,{
            when(it.status){
                ResourceState.LOADING->binding.loading.visibility=View.VISIBLE
                ResourceState.SUCCESS->{
                    binding.loading.visibility=View.INVISIBLE
                    val action=SignInFragmentDirections.actionAuthorizationFragmentToMainFragment()
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