package com.example.aiproject.presentation.ui.cars

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.sp
import com.example.aiproject.R

@Composable
internal fun CarsListScreen(
    carsList: List<CarListItem>,
    onItemClick: (item: CarListItem) -> Unit,
    isCameraGranted: Boolean,
    onRequestPermission: () -> Unit,
    onOpenCamera: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
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
fun ExpandableCameraButton(
    isCameraGranted: Boolean,
    onOpenCamera: () -> Unit,
    onRequestPermission: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    val buttonWidth by animateDpAsState(
        targetValue = if (expanded) 240.dp else 48.dp,
        animationSpec = tween(300),
        label = "buttonWidth"
    )

    Button(
        onClick = {
            if (expanded) {
                if (isCameraGranted) onOpenCamera() else onRequestPermission()
            } else {
                expanded = true
            }
        },
        modifier = modifier
            .width(buttonWidth)
            .height(48.dp),
        shape = RoundedCornerShape(32.dp),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25324C))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.CenterStart
        ) {
            this@Button.AnimatedVisibility(visible = !expanded) {
                Icon(
                    imageVector = Icons.Default.PhotoCamera,
                    contentDescription = "Камера",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(12.dp),
                    tint = Color.White
                )
            }

            this@Button.AnimatedVisibility(visible = expanded) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Свернуть",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { expanded = false },
                        tint = Color.White
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Text(
                        text = stringResource(id = R.string.take_car_picture),
                        fontSize = 17.sp,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CarsScreenPreview() {
    val sampleCars = listOf(
        CarListItem(model = "Sport 800", brand = "Toyota"),
        CarListItem(model = "Sprinter", brand = "Toyota"),
        CarListItem(model = "Stallion", brand = "Toyota"),
        CarListItem(model = "Starlet", brand = "Toyota"),
        CarListItem(model = "Super", brand = "Toyota"),
        CarListItem(model = "Supra", brand = "Toyota"),
        CarListItem(model = "Tacoma", brand = "Toyota"),
    )

    CarsListScreen(
        carsList = sampleCars,
        onItemClick = {},
        isCameraGranted = true,
        onRequestPermission = {},
        onOpenCamera = {},
    )
}
