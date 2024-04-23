package com.hearx.app.repositories

import com.hearx.app.api.RetroFitImplementation
import com.hearx.app.models.ApiResponse
import com.hearx.app.models.TestData
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

class TestRepository @Inject constructor(private val retroFitImplementation: RetroFitImplementation) {
    suspend fun postTestData(
        testData: TestData,
    ): Response<ApiResponse> {
        return retroFitImplementation.getRetrofit().postTestData(testData)
    }
}