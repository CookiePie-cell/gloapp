package com.salugan.gloapp.data.retrofit

import com.salugan.gloapp.data.retrofit.responses.DiagnoseResponse
import com.salugan.gloapp.data.retrofit.responses.LoginResponse
import com.salugan.gloapp.data.retrofit.responses.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Url

interface ApiService {

    @POST
    suspend fun login(
        @Url url: String,
        @Body requestBody: RequestBody
    ) : LoginResponse

    @POST
    suspend fun register(
        @Url url: String,
        @Body requestBody: RequestBody
    ) : RegisterResponse

    @Multipart
    @POST("predict")
    suspend fun getDiagnose(
        @Part file: MultipartBody.Part
    ) : DiagnoseResponse
}