package com.example.aiproject.data.repository

import com.example.aiproject.data.CarQueryRemoteDataSource
import com.example.aiproject.data.dto.toCarModel
import com.example.aiproject.data.dto.toDetailedInfo
import com.example.aiproject.domain.model.CarDetailedInfo
import com.example.aiproject.domain.model.CarModel
import com.example.aiproject.domain.repository.CarRepository
import javax.inject.Inject

class CarRepositoryImpl @Inject constructor(
    private val remoteDataSource: CarQueryRemoteDataSource
) : CarRepository {
    override suspend fun getModels(): List<CarModel> =
        remoteDataSource.getModels().models.map { it.toCarModel() }

    override suspend fun getModelInfo(make: String, model: String): List<CarDetailedInfo> =
        remoteDataSource.getModelInfo(make, model).trims.map { it.toDetailedInfo() }
}
