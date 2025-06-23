package com.example.aiproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.aiproject.presentation.ui.compose.components.MainScreen
import com.example.aiproject.presentation.ui.compose.components.SplashScreen
import com.example.aiproject.presentation.ui.theme.AIProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIProjectTheme {
                var showSplash by remember { mutableStateOf(true) }
                MainApp(
                    showSplash = showSplash,
                    onSplashComplete = { showSplash = false }
                )
            }
        }
    }
}


@Composable
fun MainApp(
    showSplash: Boolean,
    onSplashComplete: () -> Unit
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        if (showSplash)
            SplashScreen(onFinish = onSplashComplete)
        else MainScreen(modifier = Modifier.padding(innerPadding))
    }
}

@Preview(showBackground = true)
@Composable
fun MainAppPreview() {
    AIProjectTheme {
        MainApp(
            showSplash = false,
            onSplashComplete = {}
        )
    }
}