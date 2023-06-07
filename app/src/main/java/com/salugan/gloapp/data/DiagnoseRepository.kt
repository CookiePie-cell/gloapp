package com.salugan.gloapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.salugan.gloapp.data.retrofit.responses.DiagnoseResponse
import com.salugan.gloapp.data.retrofit.ApiService
import okhttp3.MultipartBody

class DiagnoseRepository(
    private val apiService: ApiService
) {

    fun getDiagnose(
        file: MultipartBody.Part,
    ) : LiveData<Result<DiagnoseResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDiagnose(file)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }
}
