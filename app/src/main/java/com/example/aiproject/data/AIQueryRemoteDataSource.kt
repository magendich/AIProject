package com.example.aiproject.data

import com.example.aiproject.data.api.AIApiService
import com.example.aiproject.data.dto.AiRequestBody
import com.example.aiproject.data.dto.AiResponse
import com.example.aiproject.data.dto.ContentItem
import com.example.aiproject.data.dto.ImageUrl
import com.example.aiproject.data.dto.Message
import javax.inject.Inject

class AIQueryRemoteDataSource @Inject constructor(
    private val apiService: AIApiService
) {
    suspend fun analyzeCarImage(imageUrl: String): AiResponse {
        val request = AiRequestBody(
            messages = listOf(
                Message(
                    content = listOf(
                        ContentItem(type = "text"),
                        ContentItem(
                            type = "image_url",
                            imageUrl = ImageUrl(imageUrl)
                        )
                    )
                )
            )
        )
        return apiService.analyzeImage(request)
    }
}