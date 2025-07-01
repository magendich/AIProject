package com.example.aiproject.navigation

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.aiproject.aicamera.presenation.AIAnalysisResultScreen
import com.example.aiproject.aicamera.presenation.CameraScreen
import com.example.aiproject.aicamera.presenation.CameraViewModel
import com.example.aiproject.aicamera.presenation.LoadingScreen
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
fun AppNavigation(
    carsViewModel: CarsViewModel = hiltViewModel(),
    cameraViewModel: CameraViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.CarsList.route
    ) {
        composable(NavRoutes.CarsList.route) {
            val carsState by carsViewModel.state.collectAsStateWithLifecycle()
            val permissionState: PermissionState =
                rememberPermissionState(Manifest.permission.CAMERA)

            CarsListScreen(
                carsList = carsState,
                isCameraGranted = permissionState.status.isGranted,
                onRequestPermission = { permissionState.launchPermissionRequest() },
                onOpenCamera = { navController.navigate(NavRoutes.Camera.route) },
                onItemClick = { carItem ->
                    carsViewModel.onCarSelected(carItem.brand, carItem.model) {
                        navController.navigate(NavRoutes.CarInfo.route)
                    }
                },
            )
        }

        composable(NavRoutes.CarInfo.route) {
            val carInfoState by carsViewModel.selectedCarDetail.collectAsStateWithLifecycle()

            if (carInfoState != null) {
                CarInfoScreen(
                    onBuyClick = {},
                    carItem = carInfoState!!,
                )
            }
        }

        composable(NavRoutes.Camera.route) {
            val cameraState by cameraViewModel.cameraUiState.collectAsStateWithLifecycle()
            val context = LocalContext.current

            CameraScreen(
                state = cameraState,
                onCapturePhoto = { cameraViewModel.onCapturePhoto(it) },
                onPickFromGallery = { cameraViewModel.onPickFromGallery(it) },
                onAnalyzeClick = {
                    navController.navigate(NavRoutes.Loading.route)
                    val photoUri = cameraState.photoUri
                    if (photoUri == null) return@CameraScreen

                    val bitmap = ImageUtils.getBitmapFromUri(context, photoUri)
                    if (bitmap == null) return@CameraScreen

                    val base64 = ImageUtils.bitmapToBase64(bitmap)
                    cameraViewModel.onAnalyzeClick(base64, bitmap) {
                        navController.navigate(NavRoutes.AIResult.route)
                    }
                },
                chooseAnotherPhotoClick = { cameraViewModel.resetPhotoSelection() }
            )
        }

        composable(NavRoutes.AIResult.route) {
            val cameraState by cameraViewModel.cameraUiState.collectAsStateWithLifecycle()
            if (cameraState.photoUri != null && cameraState.resultText != null) {
                AIAnalysisResultScreen(
                    imageBitmap = cameraState.imageBitmap!!,
                    resultText = cameraState.resultText!!,
                    backToMainScreen = {
                        cameraViewModel.resetPhotoSelection()
                        navController.popBackStack(NavRoutes.CarsList.route, inclusive = false)
                    }
                )
            }
        }

        composable(NavRoutes.Loading.route) {
            LoadingScreen()
        }
    }
}
