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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.aiproject.presentation.ui.camera.CameraScreen
import com.example.aiproject.presentation.ui.camera.CameraViewModel
import com.example.aiproject.presentation.ui.cars.CarModelsViewModel
import com.example.aiproject.presentation.ui.cars.CarsScreen
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun MainScreen(
    viewModel: CameraViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val cameraPermissionState: PermissionState =
        rememberPermissionState(Manifest.permission.CAMERA)
    var showCamera by remember { mutableStateOf(false) }
    val cameraUiState by viewModel.cameraUiState.collectAsStateWithLifecycle()

    val carModelsViewModel: CarModelsViewModel = hiltViewModel()
    val cars by carModelsViewModel.state.collectAsStateWithLifecycle()


    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.Center
    ) {
        if (showCamera) {
            CameraScreen(
                state = cameraUiState,
                onCapturePhoto = { viewModel.onCapturePhoto(it) },
                onPickFromGallery = { viewModel.onPickFromGallery(it) },
                onAnalyzeClick = {
                    viewModel.onAnalyzeClick()
                    showCamera = false
                }
            )
        } else {
            CarsScreen(
                carsList = cars,
                onItemClick = { },
                isCameraGranted = cameraPermissionState.status.isGranted,
                onRequestPermission = { cameraPermissionState.launchPermissionRequest() },
                onOpenCamera = { showCamera = true }
            )
        }
    }
}

