package com.salugan.gloapp.ui.activities.splash

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.asLiveData
import com.salugan.gloapp.data.local.AuthPreference
import com.salugan.gloapp.databinding.ActivitySplashBinding
import com.salugan.gloapp.ui.activities.main.MainActivity
import com.salugan.gloapp.ui.activities.authentication.AuthenticationActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        supportActionBar?.hide()
        Handler(Looper.getMainLooper()).postDelayed({
            AuthPreference.getInstance(dataStore).getSession().asLiveData().observe(this) { result ->
                if (result.isLogin) {
                    startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    finish()
                } else {
                    startActivity(Intent(this@SplashActivity, AuthenticationActivity::class.java))
                    finish()
                }
            }
        }, DURATION)
    }

    companion object {
        private const val DURATION: Long = 3000
    }
}