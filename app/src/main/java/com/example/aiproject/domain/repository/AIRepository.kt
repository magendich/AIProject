package com.example.aiproject.domain.repository

import com.example.aiproject.domain.model.AiCarModel

interface AIRepository {
    suspend fun analyzeCarImage(imageUrl: String): AiCarModel
}