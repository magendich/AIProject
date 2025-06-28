package com.example.aiproject.domain.repository

import com.example.aiproject.domain.model.CarModel

interface CarRepository {
    suspend fun getModels(): List<CarModel>
}