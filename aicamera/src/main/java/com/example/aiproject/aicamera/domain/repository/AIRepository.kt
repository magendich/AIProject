package com.example.aiproject.aicamera.domain.repository

import com.example.aiproject.aicamera.domain.model.AiCarModel

interface AIRepository {
    suspend fun analyzeCarImage(imageUrl: String): AiCarModel
}