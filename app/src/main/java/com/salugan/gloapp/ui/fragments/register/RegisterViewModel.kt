package com.salugan.gloapp.ui.fragments.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.salugan.gloapp.data.AuthRepository
import com.salugan.gloapp.data.Result
import com.salugan.gloapp.data.retrofit.responses.RegisterResponse

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel(){

    fun register(
        username: String,
        email: String,
        phone: String,
        password: String
    ) : LiveData<Result<RegisterResponse>> = authRepository.register(username, password, email, phone)
}