package com.example.aiproject.presentation.ui.cars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.aiproject.R

@Composable
internal fun CarsScreen(
    carsList: List<CarItem>,
    onItemClick: (item: CarItem) -> Unit,
    isCameraGranted: Boolean,
    onRequestPermission: () -> Unit,
    onOpenCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(bottom = 80.dp)
        ) {
            items(carsList) { car ->
                CarItemCompose(
                    item = car,
                    onItemClick = { onItemClick(car) }
                )
            }
        }

        ExpandableCameraButton(
            isCameraGranted = isCameraGranted,
            onOpenCamera = onOpenCamera,
            onRequestPermission = onRequestPermission,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        )
    }
}

@Composable
private fun ExpandableCameraButton(
    isCameraGranted: Boolean,
    onOpenCamera: () -> Unit,
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val buttonWidth by animateDpAsState(
        targetValue = if (expanded) 200.dp else 48.dp,
        animationSpec = tween(durationMillis = 300)
    )

    Button(
        onClick = {
            if (expanded) {
                if (isCameraGranted) {
                    onOpenCamera()
                } else {
                    onRequestPermission()
                }
            } else {
                expanded = true
            }
        },
        modifier = modifier
            .width(buttonWidth)
            .height(48.dp),
        contentPadding = PaddingValues(horizontal = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFDE0707))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { expanded = !expanded },
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowRight else Icons.Default.KeyboardArrowLeft,
                    contentDescription = if (expanded) "Свернуть" else "Раскрыть"
                )
            }

            this.AnimatedVisibility(
                visible = expanded,
                enter = fadeIn(animationSpec = tween(300)) + expandHorizontally(
                    animationSpec = tween(300)
                ),
                exit = fadeOut(animationSpec = tween(300)) + shrinkHorizontally(
                    animationSpec = tween(300)
                )
            ) {
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = stringResource(id = R.string.take_car_picture),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CarsScreenPreview() {
    val sampleCars = listOf(
        CarItem(model = "Sport 800", brand = "Toyota"),
        CarItem(model = "Sprinter", brand = "Toyota"),
        CarItem(model = "Stallion", brand = "Toyota"),
        CarItem(model = "Starlet", brand = "Toyota"),
        CarItem(model = "Super", brand = "Toyota"),
        CarItem(model = "Supra", brand = "Toyota"),
        CarItem(model = "Tacoma", brand = "Toyota"),
    )

    CarsScreen(
        carsList = sampleCars,
        onItemClick = {},
        isCameraGranted = true,
        onRequestPermission = {},
        onOpenCamera = {},
    )
}
