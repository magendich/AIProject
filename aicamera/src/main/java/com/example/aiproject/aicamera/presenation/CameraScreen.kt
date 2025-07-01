package com.example.aiproject.aicamera.presenation

import android.net.Uri
import android.util.Log
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.LocalLifecycleOwner
import coil.compose.rememberAsyncImagePainter
import com.example.aiproject.aicamera.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Composable
fun CameraScreen(
    state: CameraUiState,
    onCapturePhoto: (Uri) -> Unit,
    onPickFromGallery: (Uri) -> Unit,
    onAnalyzeClick: () -> Unit,
    chooseAnotherPhotoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var preview by remember { mutableStateOf<Preview?>(null) }
    var imageCapture by remember { mutableStateOf<ImageCapture?>(null) }
    var cameraError by remember { mutableStateOf<String?>(null) }
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    val galleryLauncher = rememberLauncherForActivityResult(GetContent()) { uri: Uri? ->
        if (uri != null) {
            onPickFromGallery(uri)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            val cameraProvider = cameraProviderFuture.get()
            cameraProvider.unbindAll()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .windowInsetsPadding(WindowInsets.safeDrawing),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cameraError != null) {
            Text(
                text = cameraError!!,
                color = Color.Red,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        if (state.photoUri == null) {
            AndroidView(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                factory = { ctx ->
                    val previewView = PreviewView(ctx).apply {
                        layoutParams = FrameLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                    val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
                    val cameraProvider = cameraProviderFuture.get()
                    preview = Preview.Builder().build().also {
                        it.surfaceProvider = previewView.surfaceProvider
                    }
                    imageCapture = ImageCapture.Builder().build()
                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            cameraSelector,
                            preview,
                            imageCapture
                        )
                    } catch (exc: Exception) {
                        cameraError = "Ошибка запуска камеры: ${exc.localizedMessage}"
                        Log.e("CameraScreen", "Camera bind failed", exc)
                    }
                    previewView
                }
            )
        } else {
            Image(
                painter = rememberAsyncImagePainter(state.photoUri),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        if (!state.isPhotoSelected) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        if (imageCapture == null) {
                            cameraError = "Камера не готова"
                            return@Button
                        }
                        val file = File(
                            context.cacheDir,
                            "IMG_${
                                SimpleDateFormat(
                                    "yyyyMMdd_HHmmss",
                                    Locale.US
                                ).format(Date())
                            }.jpg"
                        )
                        val outputOptions = ImageCapture.OutputFileOptions.Builder(file).build()
                        imageCapture?.takePicture(
                            outputOptions,
                            ContextCompat.getMainExecutor(context),
                            object : ImageCapture.OnImageSavedCallback {
                                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                                    onCapturePhoto(Uri.fromFile(file))
                                }

                                override fun onError(exception: ImageCaptureException) {
                                    cameraError = "Ошибка съемки: ${exception.localizedMessage}"
                                    Log.e("CameraScreen", "Photo capture failed", exception)
                                }
                            }
                        )
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25324C))
                ) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt,
                        contentDescription = "Сделать фото"
                    )
                }
                Button(
                    onClick = {
                        galleryLauncher.launch("image/*")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25324C))
                ) {
                    Icon(
                        imageVector = Icons.Default.Photo,
                        contentDescription = "Галлерея"
                    )
                }
            }
        } else {
            Column {
                Button(
                    onClick = onAnalyzeClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25324C)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        stringResource(R.string.analyze_car),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }

                Button(
                    onClick = chooseAnotherPhotoClick,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25324C)),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 8.dp)
                ) {
                    Text(
                        stringResource(R.string.choose_another_photo),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            }
        }
    }
}