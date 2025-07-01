package com.example.aiproject.aicamera.data.dto

import com.google.gson.annotations.SerializedName

data class AiRequestBody(
    val model: String = "qwen/qwen2.5-vl-72b-instruct:free",
    val messages: List<Message>
)

data class Message(
    val role: String = "user",
    val content: List<ContentItem>
)

data class ContentItem(
    val type: String,
    val text: String? = BASE_PROMPT,
    @SerializedName("image_url") val imageUrl: ImageUrl? = null
)

data class ImageUrl(
    val url: String
)


private const val BASE_PROMPT =
    "Проанализируй загруженное изображение автомобиля и кратко ответь по следующим пунктам:" +
            "\\n\\n1. Марка и модель автомобиля." +
            "\\n2. Примерный год выпуска." +
            "\\n3. Основной цвет кузова." +
            "\\n4. Предположительный пробег по внешнему виду." +
            "\\n5. Примерное количество лошадиных сил." +
            "\\n6. Примерная рыночная цена автомобиля в России." +
            "\\n\\nОтвет представь в структурированном виде:" +
            "\\n\\n— Марка и модель:" +
            "\\n— Год выпуска (примерно):" +
            "\\n— Цвет:" +
            "\\n— Пробег (примерно):" +
            "\\n— Лошадиные силы (примерно):" +
            "\\n— Примерная цена в РФ:\""
