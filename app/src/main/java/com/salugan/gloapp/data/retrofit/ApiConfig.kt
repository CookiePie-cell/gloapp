package com.salugan.gloapp.data.retrofit

import com.salugan.gloapp.BuildConfig
import com.salugan.gloapp.utils.TrustAllCerts
import com.salugan.gloapp.utils.createTrustAllSSLSocketFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        private const val BASE_URL = "https://gloappservice.site/"

        fun getApiService(): ApiService {
            val loggingInterceptor = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }

            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .readTimeout(30, TimeUnit.SECONDS) // Increase the timeout duration (e.g., 30 seconds)
                .sslSocketFactory(createTrustAllSSLSocketFactory()!!, TrustAllCerts()) // Bypass SSL certificate validation
                .hostnameVerifier { _, _ -> true } // Bypass hostname verification
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}