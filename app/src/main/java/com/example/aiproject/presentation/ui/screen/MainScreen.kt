package com.example.aiproject.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.aiproject.R
import com.example.aiproject.navigation.AppNavigation
import com.example.aiproject.navigation.NavRoutes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(getCurrentScreenTitleId(currentRoute.toString())),
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    )
                },
                navigationIcon = {
                    if (currentRoute != NavRoutes.CarsList.route &&
                        currentRoute != NavRoutes.AIResult.route &&
                        currentRoute != NavRoutes.Loading.route
                    ) {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
                        }
                    }
                },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            AppNavigation(navController = navController)
        }
    }
}

private fun getCurrentScreenTitleId(route: String): Int =
    when (route) {
        NavRoutes.CarsList.route -> R.string.cars_list_title
        NavRoutes.CarInfo.route -> R.string.car_info_title
        NavRoutes.Camera.route -> R.string.camera_title
        NavRoutes.AIResult.route -> R.string.ai_result_title
        NavRoutes.Loading.route -> R.string.analysis_car
        else -> R.string.app_default_title
    }
