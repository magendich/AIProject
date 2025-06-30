package com.example.aiproject.domain.usecase

import com.example.aiproject.domain.model.CarDetailedInfo
import com.example.aiproject.domain.repository.CarRepository
import javax.inject.Inject

class GetCarDetailedInfoUseCase @Inject constructor(
    val carRepository: CarRepository
) {
    suspend operator fun invoke(make: String, model: String): List<CarDetailedInfo> =
        carRepository.getModelInfo(make, model)
}