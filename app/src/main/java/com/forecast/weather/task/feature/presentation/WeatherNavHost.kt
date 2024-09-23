package com.forecast.weather.task.feature.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun WeatherNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "cityInput") {
        composable("cityInput") {
            CityInputScreen(navController = navController)
        }
        composable("weatherDisplay/{cityName}") { backStackEntry ->
            val cityName = backStackEntry.arguments?.getString("cityName")
            cityName?.let {
                WeatherDisplayScreen(cityName = it)
            }
        }
        composable("weatherDisplay/{latitude}/{longitude}") { backStackEntry ->
            val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull()
            val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull()
            if (latitude != null && longitude != null) {
                WeatherDisplayScreen(latitude = latitude, longitude = longitude)
            }
        }
    }
}
