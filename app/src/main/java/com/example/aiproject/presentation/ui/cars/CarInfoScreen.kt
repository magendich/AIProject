package com.example.aiproject.presentation.ui.cars

import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.net.toUri
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.example.aiproject.R

@Composable
internal fun CarInfoScreen(carItem: CarCardItem, onBuyClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CarInfoCard(
            headline = carItem.headline,
            subhead = carItem.subhead,
            country = carItem.country,
            horsepower = carItem.horsepower,
            topSpeedKph = carItem.topSpeedKph,
            transmissionType = carItem.transmissionType,
            driveType = carItem.driveType,
            onBuyClick = onBuyClick
        )
    }
}

@Composable
private fun CarInfoCard(
    headline: String,
    subhead: String,
    country: String?,
    horsepower: String?,
    topSpeedKph: String?,
    transmissionType: String?,
    driveType: String?,
    onBuyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    val isPreview = LocalInspectionMode.current

    val player = remember {
        if (!isPreview) {
            ExoPlayer.Builder(context).build().apply {
                val videoUri = "android.resource://${context.packageName}/${R.raw.car_video}".toUri()
                setMediaItem(MediaItem.fromUri(videoUri))
                prepare()
                playWhenReady = true
                repeatMode = Player.REPEAT_MODE_ALL
            }
        } else {
            null
        }
    }

    DisposableEffect(player) {
        onDispose { player?.release() }
    }

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (!isPreview) {
                AndroidView(
                    factory = {
                        PlayerView(context).apply {
                            this.player = player
                            useController = true
                            layoutParams = ViewGroup.LayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                with(density) { 180.dp.toPx().toInt() }
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .aspectRatio(16f / 9f)
                )
            } else {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                )
            }

            Text(
                text = headline,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
            )

            Text(
                text = subhead,
                fontSize = 16.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Column {
                if (!country.isNullOrBlank()) InfoRow(labelResId = R.string.label_country, value = country)
                if (!horsepower.isNullOrBlank()) InfoRow(labelResId = R.string.label_horsepower, value = horsepower)
                if (!topSpeedKph.isNullOrBlank()) InfoRow(labelResId = R.string.label_top_speed, value = topSpeedKph)
                if (!transmissionType.isNullOrBlank()) {
                    InfoRow(
                        labelResId = R.string.label_transmission,
                        value = transmissionType
                    )
                }
                if (!driveType.isNullOrBlank()) InfoRow(labelResId = R.string.label_drive, value = driveType)
            }

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF25324C)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(),
                        onClick = onBuyClick
                    ),
                onClick = onBuyClick,
            ) {
                Text(
                    text = stringResource(R.string.button_order),
                    fontSize = 16.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun InfoRow(labelResId: Int, value: String) {
    Row(
        modifier = Modifier.padding(vertical = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(id = labelResId) + ":",
            fontWeight = FontWeight.SemiBold,
            color = Color.DarkGray,
            modifier = Modifier.width(180.dp)
        )
        Text(
            text = value,
            color = Color.Black
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun CarInfoCardPreview() {
    CarInfoCard(
        headline = "Toyota",
        subhead = "Camry, 2023",
        country = "США",
        horsepower = "250",
        topSpeedKph = "210",
        transmissionType = "Автомат",
        driveType = "Передний привод",
        onBuyClick = {},
    )
}
