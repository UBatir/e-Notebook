package com.example.enotebook

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.example.enotebook.data.local.SharedPreferences
import com.example.enotebook.databinding.ActivityMainBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {

    private val settings: SharedPreferences by inject()
    private lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setLocale(settings.language)
        setContentView(binding.root)
        findNavController(R.id.nav_host_fragment)
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.SEND_SMS,
                        Manifest.permission.READ_CONTACTS
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) { /* ... */
                    }

                    override fun onPermissionRationaleShouldBeShown(
                            permissions: List<com.karumi.dexter.listener.PermissionRequest>, token: PermissionToken
                    ) { /* ... */
                    }
                }).check()
    }

    private fun setLocale(localeCode:String) {
        val resources = resources
        val dm = resources.displayMetrics
        val config = resources.configuration
        val local=Locale(localeCode)
        config.setLocale(local)
        resources.updateConfiguration(config, dm)
    }
}