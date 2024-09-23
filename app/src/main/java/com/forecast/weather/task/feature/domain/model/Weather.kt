package com.forecast.weather.task.feature.domain.model


data class Weather(
    val name: String,
    val description: String,
    val icon: String,
    val temp: Double,
    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Int,
    val humidity: Int,
    val visibility: Int,
    val windSpeed: Double
)
