package com.example.aiproject.presentation.ui.camera

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
internal fun AIAnalysisResultScreen(
    imageBitmap: Bitmap,
    resultText: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Image(
                bitmap = imageBitmap.asImageBitmap(),
                contentDescription = "Фото авто",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .padding(16.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
            Text(
                text = resultText,
                style = MaterialTheme.typography.labelLarge,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun AIAnalysisResultScreenPreview() {

    val dummyBitmap = Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888).apply {
        eraseColor(android.graphics.Color.DKGRAY)
    }

    val dummyText = """
        — Марка и модель: Toyota Camry  
        — Год выпуска (примерно): 2018–2020  
        — Цвет: Черный  
        — Пробег (примерно): 50 000–100 000 км  
        — Лошадиные силы (примерно): 200–250 л.с.  
        — Примерная цена в РФ: 2 000 000 – 2 500 000 рублей
    """.trimIndent()

    AIAnalysisResultScreen(
        imageBitmap = dummyBitmap,
        resultText = dummyText,
        modifier = Modifier.padding(16.dp),
    )
}