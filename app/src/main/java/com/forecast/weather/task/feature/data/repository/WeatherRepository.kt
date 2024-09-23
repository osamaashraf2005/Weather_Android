package com.forecast.weather.task.feature.data.repository

import com.forecast.weather.task.feature.data.model.WeatherResponse

interface WeatherRepository {

    suspend fun getWeatherByCity(
        cityName: String,
        units: String = "metric"
    ): WeatherResponse

    suspend fun getWeatherByLocation(
        latitude: Double,
        longitude: Double,
        units: String = "metric"
    ): WeatherResponse
}
