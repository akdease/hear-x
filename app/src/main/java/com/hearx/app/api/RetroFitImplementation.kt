package com.hearx.app.api

import com.hearx.app.utilities.AppConstants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroFitImplementation {
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HearxInterceptor())
        .build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    private fun getBaseUrl(): String {
        return AppConstants.API_HOST
    }

    fun getRetrofit(): RetrofitService {
        return retrofit.create(RetrofitService::class.java)
    }
}