package com.example.aiproject.aicamera.data.api

import com.example.aiproject.aicamera.data.dto.AiRequestBody
import com.example.aiproject.aicamera.data.dto.AiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

private const val API_KEY =
    "Bearer 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"

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