package com.example.aiproject.domain.usecase

import com.example.aiproject.domain.model.CarModel
import com.example.aiproject.domain.repository.CarRepository
import javax.inject.Inject

class GetCarModelsUseCase @Inject constructor(
    private val carRepository: CarRepository
) {
    suspend operator fun invoke(): List<CarModel> = carRepository.getModels()
}
