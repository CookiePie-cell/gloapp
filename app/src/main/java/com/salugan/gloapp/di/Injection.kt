package com.salugan.gloapp.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.protobuf.Api
import com.salugan.gloapp.data.AuthRepository
import com.salugan.gloapp.data.DiagnoseRepository
import com.salugan.gloapp.data.local.AuthPreference
import com.salugan.gloapp.data.retrofit.ApiConfig
import com.salugan.gloapp.data.retrofit.ApiService

object Injection {
    fun provideDiagnoseRepository() : DiagnoseRepository {
        val apiService: ApiService = ApiConfig.getApiService()
        return DiagnoseRepository(apiService)
    }

    fun provideAuthRepository() : AuthRepository {
        val apiService: ApiService = ApiConfig.getApiService()
        return AuthRepository(apiService)
    }

    fun providePreferences(dataStore: DataStore<Preferences>): AuthPreference {
        return AuthPreference.getInstance(dataStore)
    }
}