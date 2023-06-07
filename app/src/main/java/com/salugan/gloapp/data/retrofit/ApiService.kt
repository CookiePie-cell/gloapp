package com.salugan.gloapp.data.retrofit

import com.salugan.gloapp.data.retrofit.responses.DiagnoseResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @Multipart
    @POST("predict")
    suspend fun getDiagnose(
        @Part file: MultipartBody.Part
    ) : DiagnoseResponse
}