package com.example.aiproject.presentation.ui.compose.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.aiproject.R
import com.example.aiproject.presentation.ui.screen.camera.CameraScreen
import com.example.aiproject.presentation.ui.screen.camera.CameraUiState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier
) {
    val cameraPermissionState: PermissionState =
        rememberPermissionState(android.Manifest.permission.CAMERA)
    var showCamera by remember { mutableStateOf(false) }
    var cameraUiState by remember { mutableStateOf(CameraUiState()) }

    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.safeDrawing),
        contentAlignment = Alignment.Center
    ) {
        if (showCamera) {
            CameraScreen(
                state = cameraUiState,
                onCapturePhoto = { uri ->
                    cameraUiState = cameraUiState.copy(photoUri = uri, isPhotoSelected = true)
                },
                onPickFromGallery = { uri ->
                    cameraUiState = cameraUiState.copy(photoUri = uri, isPhotoSelected = true)
                },
                onAnalyzeClick = {
                    cameraUiState = cameraUiState.copy(photoUri = null, isPhotoSelected = false)
                    showCamera = false
                }
            )
        } else {
            Button(
                onClick = {
                    if (cameraPermissionState.status.isGranted) {
                        showCamera = true
                    } else {
                        cameraPermissionState.launchPermissionRequest()
                    }
                },
                modifier = Modifier.padding(16.dp),
            ) {
                Text(text = stringResource(id = R.string.take_car_picture))
            }
        }
    }
}

