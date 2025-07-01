package com.example.aiproject.aicamera.data.api

import com.example.aiproject.aicamera.data.dto.AiRequestBody
import com.example.aiproject.aicamera.data.dto.AiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

private const val API_KEY = "api_key"

interface AIApiService {

    @POST("api/v1/chat/completions")
    suspend fun analyzeImage(
        @Body body: AiRequestBody,
        @Header("Authorization") authHeader: String = API_KEY,
        @Header("Content-Type") contentType: String = "application/json",
        @Header("HTTP-Referer") referer: String? = null,
        @Header("X-Title") title: String? = null,
    ): AiResponse
}