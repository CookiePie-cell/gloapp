package com.salugan.gloapp.ui.fragments.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salugan.gloapp.data.AuthRepository
import com.salugan.gloapp.data.retrofit.responses.LoginResponse
import com.salugan.gloapp.data.Result

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel(){

    fun login(username: String, password: String)
    : LiveData<Result<LoginResponse>> = authRepository.login(username, password)
}