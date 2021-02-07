package com.example.enotebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.enotebook.databinding.ActivityMainBinding
import com.example.enotebook.utils.APP_ACTIVITY

class MainActivity : AppCompatActivity() {

    lateinit var mToolbar: Toolbar
    lateinit var mNavController:NavController
    private var _binding:ActivityMainBinding?=null
    val mBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        APP_ACTIVITY=this
        mNavController=Navigation.findNavController(this,R.id.nav_host_fragment)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}