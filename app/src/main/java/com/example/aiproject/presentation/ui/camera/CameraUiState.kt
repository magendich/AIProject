package com.example.aiproject.presentation.ui.camera

import android.graphics.Bitmap
import android.net.Uri

data class CameraUiState(
    val photoUri: Uri? = null,
    val isPhotoSelected: Boolean = false,
    val errorMessage: String? = null,
    val resultText: String? = null,
    val imageBitmap: Bitmap? = null
)
