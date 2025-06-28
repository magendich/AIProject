package com.example.aiproject.data.api

import com.example.aiproject.data.dto.GetModelsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CarApiService {
    @GET("api/0.3/")
    suspend fun getModels(
        @Query("cmd") cmd: String = "getModels",
        @Query("make") make: String = "toyota",
        @Query("limit") limit: Int = 15
    ): GetModelsResponse
}