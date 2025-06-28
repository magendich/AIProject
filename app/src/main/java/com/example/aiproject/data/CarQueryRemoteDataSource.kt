package com.example.aiproject.data

import com.example.aiproject.data.api.CarApiService
import javax.inject.Inject

class CarQueryRemoteDataSource @Inject constructor(
    private val apiService: CarApiService
) {
    suspend fun getModels() = apiService.getModels()
}