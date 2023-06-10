package com.salugan.gloapp.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.salugan.gloapp.data.AuthRepository
import com.salugan.gloapp.data.retrofit.responses.LoginResponse
import com.salugan.gloapp.data.Result
import com.salugan.gloapp.data.local.AuthPreference
import com.salugan.gloapp.data.local.SessionModel
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authRepository: AuthRepository,
    private val authPreference: AuthPreference
) : ViewModel(){

    fun login(username: String, password: String)
    : LiveData<Result<LoginResponse>> = authRepository.login(username, password)

    fun saveSession(name: String, token: String, isLogin: Boolean) {
        viewModelScope.launch {
            authPreference.saveSession(SessionModel(name, token, isLogin))
        }
    }
}