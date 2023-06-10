package com.salugan.gloapp.ui.fragments.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.salugan.gloapp.data.AuthRepository
import com.salugan.gloapp.data.Result
import com.salugan.gloapp.data.local.AuthPreference
import com.salugan.gloapp.data.local.SessionModel
import com.salugan.gloapp.data.retrofit.responses.UserResponse
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileViewModel(
    private val authRepository: AuthRepository,
    private val authPreference: AuthPreference
) : ViewModel() {

    fun getSession(): LiveData<SessionModel> {
        return authPreference.getSession().asLiveData()
    }

    fun clearSession() {
        runBlocking {
            launch {
                authPreference.clearSession()
            }
        }
    }
    fun getUser(username: String) : LiveData<Result<UserResponse>> = authRepository.getUser(username)
}