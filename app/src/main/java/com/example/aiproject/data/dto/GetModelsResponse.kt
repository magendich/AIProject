package com.example.aiproject.data.dto

import com.google.gson.annotations.SerializedName

data class GetModelsResponse(
    @SerializedName("Models") val models: List<CarModelDto>
)

data class CarModelDto(
    @SerializedName("model_name") val name: String,
    @SerializedName("model_make_id") val brand: String,
)