package com.hearx.app.api

import com.hearx.app.models.TestData
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("/")
    suspend fun postTestData(
        @Body testData: TestData
    ): ResponseBody
}