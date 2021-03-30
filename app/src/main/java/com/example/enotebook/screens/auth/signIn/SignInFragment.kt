package com.example.enotebook.screens.auth.signIn

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.R
import com.example.enotebook.databinding.FragmentSignInBinding
import com.example.enotebook.screens.extentions.BaseFragment
import com.example.enotebook.screens.extentions.ResourceState
import com.example.enotebook.screens.extentions.onClick
import com.example.enotebook.utils.SharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class SignInFragment : BaseFragment(R.layout.fragment_sign_in){

    private lateinit var navController: NavController
    private lateinit var binding: FragmentSignInBinding
    private val viewModel: SignInViewModel by viewModel()
    private lateinit var sharedPreferences: SharedPreferences
    companion object{
        private const val SIGN_IN:Int=1
    }
    private val mGoogleSignInClient:GoogleSignInClient by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController=Navigation.findNavController(view)
        val _binding=FragmentSignInBinding.bind(view)
        sharedPreferences= SharedPreferences(requireContext())
        sharedPreferences.setFirstLaunched()
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
            googleLogin.onClick {
                signIn()
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

    private fun signIn(){
        val signInIntent: Intent =mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode== SIGN_IN){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
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
                Toast.makeText(requireContext(), e.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun setUpObserversGoogle() {
        viewModel.signInGoogle.observe(viewLifecycleOwner,{
            when(it.status){
                ResourceState.SUCCESS->{
                    binding.loading.visibility=View.GONE
                    val action=SignInFragmentDirections.actionAuthorizationFragmentToMainFragment()
                    navController.navigate(action)
                }
                ResourceState.ERROR->{
                    binding.loading.visibility=View.GONE
                    toastLN(it.message)
                }
                ResourceState.LOADING->binding.loading.visibility=View.VISIBLE
            }
        })
    }
}