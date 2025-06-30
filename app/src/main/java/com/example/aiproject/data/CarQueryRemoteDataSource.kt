package com.example.aiproject.data

import com.example.aiproject.data.api.CarApiService
import com.example.aiproject.data.dto.GetModelInfoResponse
import com.example.aiproject.data.dto.GetModelsResponse
import javax.inject.Inject

class CarQueryRemoteDataSource @Inject constructor(
    private val apiService: CarApiService
) {
    suspend fun getModels(): GetModelsResponse = apiService.getModels()

    suspend fun getModelInfo(make: String, model: String): GetModelInfoResponse =
        apiService.getModelInfo(make = make, model = model)
}