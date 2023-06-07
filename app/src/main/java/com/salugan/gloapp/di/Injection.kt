package com.salugan.gloapp.di

import com.salugan.gloapp.data.DiagnoseRepository
import com.salugan.gloapp.data.retrofit.ApiConfig
import com.salugan.gloapp.data.retrofit.ApiService

object Injection {
    fun provideDiagnoseRepository() : DiagnoseRepository {
        val apiService: ApiService = ApiConfig.getApiService()
        return DiagnoseRepository(apiService)
    }
}