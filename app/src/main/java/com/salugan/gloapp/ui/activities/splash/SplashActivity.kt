package com.salugan.gloapp.ui.activities.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.salugan.gloapp.databinding.ActivitySplashBinding
import com.salugan.gloapp.ui.activities.MainActivity
import com.salugan.gloapp.ui.activities.authentication.AuthenticationActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, AuthenticationActivity::class.java))
            finish()
        }, DURATION)
    }

    companion object {
        private const val DURATION: Long = 3000
    }
}