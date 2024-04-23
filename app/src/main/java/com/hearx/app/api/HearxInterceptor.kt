package com.hearx.app.api

import okhttp3.Interceptor
import okhttp3.Response

class HearxInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val theBuilders = originalRequest.newBuilder()
            .header("Content-Type", "application/json")
        val request = theBuilders.build()
        return chain.proceed(request)
    }
}