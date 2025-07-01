package com.example.aiproject.aicamera.data.repository

import com.example.aiproject.aicamera.data.AIQueryRemoteDataSource
import com.example.aiproject.aicamera.domain.model.AiCarModel
import com.example.aiproject.aicamera.domain.repository.AIRepository
import javax.inject.Inject

class AIRepositoryImpl @Inject constructor(
    private val remoteDataSource: AIQueryRemoteDataSource
) : AIRepository {
    override suspend fun analyzeCarImage(imageUrl: String): AiCarModel {
        val response = remoteDataSource.analyzeCarImage(imageUrl)
        val content =
            response.choices.firstOrNull()?.response?.content ?: "Не удалось получить данные"
        return AiCarModel(description = content)
    }
}