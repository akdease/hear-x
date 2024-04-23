package com.hearx.app.api

import com.hearx.app.models.ApiResponse
import com.hearx.app.models.TestData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface RetrofitService {
    @POST("/")
    suspend fun postTestData(
        @Body testData: TestData
    ): Response<ApiResponse>
}