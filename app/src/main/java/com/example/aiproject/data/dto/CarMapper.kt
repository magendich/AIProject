package com.example.aiproject.data.dto

import com.example.aiproject.domain.model.CarDetailedInfo
import com.example.aiproject.domain.model.CarModel

fun CarModelDto.toCarModel(): CarModel =
    CarModel(
        name = this.name,
        brand = this.brand
    )

fun TrimsDto.toDetailedInfo(): CarDetailedInfo =
    CarDetailedInfo(
        brand = this.brand,
        model = this.model,
        year = this.year,
        country = this.country,
        horsepower = this.horsepower,
        topSpeedKph = this.topSpeedKph,
        transmissionType = this.transmissionType,
        driveType = this.driveType
    )
