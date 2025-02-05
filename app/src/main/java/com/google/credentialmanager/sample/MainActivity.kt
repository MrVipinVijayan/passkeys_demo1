
package com.google.credentialmanager.sample

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.credentialmanager.sample.HomeFragment.HomeFragmentCallback
import com.google.credentialmanager.sample.MainFragment.MainFragmentCallback
import com.google.credentialmanager.sample.R.id
import com.google.credentialmanager.sample.SignInFragment.SignInFragmentCallback
import com.google.credentialmanager.sample.SignUpFragment.SignUpFragmentCallback
import com.google.credentialmanager.sample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), MainFragmentCallback, HomeFragmentCallback,
    SignInFragmentCallback, SignUpFragmentCallback {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DataProvider.initSharedPref(applicationContext)

        if (DataProvider.isSignedIn()) {
            showHome()
        } else {
            loadMainFragment()
        }
    }

    override fun signup() {
        loadFragment(SignUpFragment(), false)
    }

    override fun signIn() {
        loadFragment(SignInFragment(), false)
    }

    override fun checkPlatformAuthenticators() {

    }

    override fun logout() {
        supportFragmentManager.popBackStack("home", FragmentManager.POP_BACK_STACK_INCLUSIVE)
        loadMainFragment()
    }

    private fun loadMainFragment() {
        supportFragmentManager.popBackStack()
        loadFragment(MainFragment(), false)
    }

    override fun showHome() {
        supportFragmentManager.popBackStack()
        loadFragment(HomeFragment(), true, "home")
    }

    private fun loadFragment(fragment: Fragment, flag: Boolean, backstackString: String? = null) {
        DataProvider.configureSignedInPref(flag)
        supportFragmentManager.beginTransaction().replace(id.fragment_container, fragment)
            .addToBackStack(backstackString).commit()
    }

    override fun onBackPressed() {
        if (DataProvider.isSignedIn() || supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}
