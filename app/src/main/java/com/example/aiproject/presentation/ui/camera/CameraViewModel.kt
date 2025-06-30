package com.example.aiproject.presentation.ui.camera

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aiproject.domain.usecase.GetCarImageAIDescriptionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val getCarImageAIDescriptionUseCase: GetCarImageAIDescriptionUseCase
) : ViewModel() {

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

    fun onAnalyzeClick(base64Image: String, bitmap: Bitmap) {
        if (base64Image.isBlank()) {
            Log.e("CameraViewModel", "Image Base64 is null or empty")
            return
        }

        viewModelScope.launch {
            try {
                val result = getCarImageAIDescriptionUseCase(base64Image)
                Log.d("CameraViewModel", "AI result: ${result.description}")

                _cameraUiState.update {
                    it.copy(
                        resultText = result.description,
                        imageBitmap = bitmap,
                        photoUri = null,
                        isPhotoSelected = false
                    )
                }
            } catch (e: Exception) {
                Log.e("CameraViewModel", "AI request failed: ${e.localizedMessage}", e)
                _cameraUiState.update {
                    it.copy(errorMessage = "Не удалось проанализировать изображение")
                }
            }
        }
    }
}