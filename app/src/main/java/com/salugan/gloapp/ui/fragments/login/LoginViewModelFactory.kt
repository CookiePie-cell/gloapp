package com.salugan.gloapp.ui.fragments.login

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salugan.gloapp.data.AuthRepository
import com.salugan.gloapp.data.local.AuthPreference
import com.salugan.gloapp.di.Injection

class LoginViewModelFactory private constructor(
    private val authRepository: AuthRepository,
    private val authPreference: AuthPreference,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authRepository, authPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: LoginViewModelFactory? = null
        fun getInstance(dataStore: DataStore<Preferences>): LoginViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: LoginViewModelFactory(Injection.provideAuthRepository(), Injection.providePreferences(dataStore))
            }.also { instance = it }
    }
}