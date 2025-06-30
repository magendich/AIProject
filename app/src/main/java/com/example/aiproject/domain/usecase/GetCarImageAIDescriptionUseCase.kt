package com.example.aiproject.domain.usecase

import com.example.aiproject.domain.repository.AIRepository
import javax.inject.Inject

class GetCarImageAIDescriptionUseCase @Inject constructor(
    private val carAIRepository: AIRepository
) {
    suspend operator fun invoke(imageUrl: String) = carAIRepository.analyzeCarImage(imageUrl)
}