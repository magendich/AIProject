package com.example.aiproject.data.dto

import com.google.gson.annotations.SerializedName

data class GetModelInfoResponse(
    @SerializedName("Trims") val trims: List<TrimsDto>
)

data class TrimsDto(
    @SerializedName("model_make_id") val brand: String,
    @SerializedName("model_name") val model: String,
    @SerializedName("model_year") val year: Int?,
    @SerializedName("make_country") val country: String?,
    @SerializedName("model_engine_power_ps") val horsepower: Int?,
    @SerializedName("model_top_speed_kph") val topSpeedKph: Int?,
    @SerializedName("model_transmission_type") val transmissionType: String?,
    @SerializedName("model_drive") val driveType: String?
)
