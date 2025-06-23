package com.example.aiproject.presentation.ui.screen.camera

import android.net.Uri

data class CameraUiState(
    val photoUri: Uri? = null,
    val isPhotoSelected: Boolean = false,
    val errorMessage: String? = null
)
