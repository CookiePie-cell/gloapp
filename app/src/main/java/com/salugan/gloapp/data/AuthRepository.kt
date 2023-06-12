package com.salugan.gloapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.salugan.gloapp.data.retrofit.ApiService
import com.salugan.gloapp.data.retrofit.responses.LoginResponse
import com.salugan.gloapp.data.retrofit.responses.RegisterResponse
import com.salugan.gloapp.data.retrofit.responses.UserResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

class AuthRepository(
    private val apiService: ApiService
) {

    fun getUser(username: String) : LiveData<Result<UserResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getUser(BASE_URL + "user/" + username)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun login(username: String, password: String) : LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val requestBody = JSONObject().apply {
                put("username", username)
                put("password", password)
            }.toString().toRequestBody("application/json".toMediaType())

            val response = apiService.login(BASE_URL + "login", requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    fun register(username: String, password: String, email: String, phone: String)
    : LiveData<Result<RegisterResponse>> = liveData {
        emit(Result.Loading)
        try {
            val requestBody = JSONObject().apply {
                put("username", username)
                put("password", password)
                put("email", email)
                put("mobile", phone)
            }.toString().toRequestBody("application/json".toMediaType())

            val response = apiService.register(BASE_URL + "register", requestBody)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        const val BASE_URL = "https://gloapp-389203.et.r.appspot.com/gloapp/"
    }
}