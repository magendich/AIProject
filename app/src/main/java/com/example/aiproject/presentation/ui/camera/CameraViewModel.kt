package com.example.aiproject.presentation.ui.camera

import android.net.Uri
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor() : ViewModel() {

    private val _cameraUiState = MutableStateFlow(CameraUiState())
    val cameraUiState: StateFlow<CameraUiState> = _cameraUiState.asStateFlow()

    fun onCapturePhoto(uri: Uri) =
        _cameraUiState.update {
            it.copy(photoUri = uri, isPhotoSelected = true)
        }

    fun onPickFromGallery(uri: Uri) =
        _cameraUiState.update {
            it.copy(photoUri = uri, isPhotoSelected = true)
        }

    fun onAnalyzeClick() =
        _cameraUiState.update {
            it.copy(photoUri = null, isPhotoSelected = false)
        }
}