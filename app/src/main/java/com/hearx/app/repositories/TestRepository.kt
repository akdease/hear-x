package com.hearx.app.repositories

import com.hearx.app.api.RetroFitImplementation
import com.hearx.app.models.TestData
import okhttp3.ResponseBody
import javax.inject.Inject

class TestRepository @Inject constructor(private val retroFitImplementation: RetroFitImplementation) {
    suspend fun postTestData(
        testData: TestData,
    ): ResponseBody {
        return retroFitImplementation.getRetrofit().postTestData(testData)
    }
}