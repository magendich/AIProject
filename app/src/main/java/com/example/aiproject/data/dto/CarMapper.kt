package com.example.aiproject.data.dto

import com.example.aiproject.domain.model.CarModel

fun CarModelDto.toCarModel(): CarModel =
    CarModel(
        name = this.name,
        brand = this.brand
    )