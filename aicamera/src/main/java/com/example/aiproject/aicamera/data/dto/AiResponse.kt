package com.example.aiproject.aicamera.data.dto

import com.google.gson.annotations.SerializedName

data class AiResponse(
    val choices: List<Choice>
)

data class Choice(
    @SerializedName("message") val response: Response
)

data class Response(
    val content: String
)
