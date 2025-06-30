package com.example.aiproject.navigation

sealed class NavRoutes(val route: String) {
    object CarsList : NavRoutes("cars_list")
    object CarInfo : NavRoutes("car_info")
    object Camera : NavRoutes("camera")
    object AIResult : NavRoutes("ai_result")
    object Loading : NavRoutes("loading")
}