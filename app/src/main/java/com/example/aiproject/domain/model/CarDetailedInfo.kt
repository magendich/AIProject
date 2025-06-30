package com.example.aiproject.domain.model

data class CarDetailedInfo(
    val brand: String,
    val model: String,
    val year: Int?,
    val country: String?,
    val horsepower: Int?,
    val topSpeedKph: Int?,
    val transmissionType: String?,
    val driveType: String?
)
