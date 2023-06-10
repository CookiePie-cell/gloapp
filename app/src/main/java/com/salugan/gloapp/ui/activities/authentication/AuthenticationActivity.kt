package com.salugan.gloapp.ui.activities.authentication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.commitNow
import com.salugan.gloapp.R
import com.salugan.gloapp.databinding.ActivityAuthenticationBinding
import com.salugan.gloapp.ui.fragments.login.LoginFragment
import com.salugan.gloapp.ui.fragments.register.RegisterFragment

class AuthenticationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthenticationBinding

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            val currentFragment = supportFragmentManager.findFragmentById(R.id.auth_fragment)
            if (currentFragment is RegisterFragment) {
                supportFragmentManager.commitNow {
                    replace(R.id.auth_fragment, LoginFragment(), LoginFragment::class.java.simpleName)
                }
            } else {
                finish()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAuthenticationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.onBackPressedDispatcher.addCallback(onBackPressedCallback)

        val mFragmentManager = supportFragmentManager
        val mLoginFragment = LoginFragment()
        val fragment = mFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

        if (fragment !is LoginFragment) {
            mFragmentManager.commitNow(allowStateLoss = true) {
                replace(R.id.auth_fragment, mLoginFragment, LoginFragment::class.java.simpleName)
            }
        }
    }
}