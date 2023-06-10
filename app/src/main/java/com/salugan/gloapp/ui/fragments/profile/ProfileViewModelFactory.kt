package com.salugan.gloapp.ui.fragments.profile

import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.salugan.gloapp.data.AuthRepository
import com.salugan.gloapp.data.local.AuthPreference
import com.salugan.gloapp.di.Injection
import androidx.datastore.preferences.core.Preferences

class ProfileViewModelFactory private constructor(
    private val authRepository: AuthRepository,
    private val authPreference: AuthPreference
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(authRepository, authPreference) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ProfileViewModelFactory? = null
        fun getInstance(datastore: DataStore<Preferences>): ProfileViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ProfileViewModelFactory(Injection.provideAuthRepository(), Injection.providePreferences(datastore))
            }.also { instance = it }
    }
}