package com.example.aiproject.presentation.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aiproject.presentation.ui.camera.AIAnalysisResultScreen
import com.example.aiproject.presentation.ui.camera.CameraScreen
import com.example.aiproject.presentation.ui.camera.CameraViewModel
import com.example.aiproject.presentation.ui.cars.CarCardItem
import com.example.aiproject.presentation.ui.cars.CarInfoScreen
import com.example.aiproject.presentation.ui.cars.CarsListScreen
import com.example.aiproject.presentation.ui.cars.CarsViewModel
import com.example.aiproject.utils.ImageUtils
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun MainScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    carsViewModel: CarsViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(Manifest.permission.CAMERA)
    var showCamera by remember { mutableStateOf(false) }
    val cameraUiState by viewModel.cameraUiState.collectAsStateWithLifecycle()
    val cars by carsViewModel.state.collectAsStateWithLifecycle()

    var selectedCar by remember { mutableStateOf<CarCardItem?>(null) }

    val context = LocalContext.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.Center
    ) {
        if (cameraUiState.resultText != null && cameraUiState.imageBitmap != null) {
            AIAnalysisResultScreen(
                imageBitmap = cameraUiState.imageBitmap!!,
                resultText = cameraUiState.resultText!!,
            )
        } else if (showCamera) {
            CameraScreen(
                state = cameraUiState,
                onCapturePhoto = { viewModel.onCapturePhoto(it) },
                onPickFromGallery = { viewModel.onPickFromGallery(it) },
                onAnalyzeClick = {
                    val photoUri = cameraUiState.photoUri
                    if (photoUri != null) {
                        val bitmap = ImageUtils.getBitmapFromUri(context, photoUri)
                        if (bitmap != null) {
                            val base64 = ImageUtils.bitmapToBase64(bitmap)
                            viewModel.onAnalyzeClick(base64, bitmap)
                            showCamera = false
                        }
                    }
                }
            )
        } else {
            if (selectedCar == null) {
                CarsListScreen(
                    carsList = cars,
                    onItemClick = { carItem ->
                        carsViewModel.onCarSelected(carItem.brand, carItem.model) {
                            selectedCar = carsViewModel.selectedCarDetail.value
                        }
                    },
                    isCameraGranted = cameraPermissionState.status.isGranted,
                    onRequestPermission = { cameraPermissionState.launchPermissionRequest() },
                    onOpenCamera = { showCamera = true }
                )
            } else {
                CarInfoScreen(
                    carItem = selectedCar!!,
                    onBuyClick = {}
                )
            }
        }
    }
}


