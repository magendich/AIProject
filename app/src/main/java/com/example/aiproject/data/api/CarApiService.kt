package com.example.aiproject.data.api

import com.example.aiproject.data.dto.GetModelInfoResponse
import com.example.aiproject.data.dto.GetModelsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface CarApiService {
    @GET("api/0.3/")
    suspend fun getModels(
        @Query("cmd") cmd: String = "getModels",
        @Query("make") make: String = "toyota"
    ): GetModelsResponse

    @GET("api/0.3/")
    suspend fun getModelInfo(
        @Query("cmd") cmd: String = "getTrims",
        @Query("make") make: String,
        @Query("model") model: String
    ): GetModelInfoResponse
}