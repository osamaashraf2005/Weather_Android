package com.forecast.weather.task.feature.data.datasource.network.repository

import com.forecast.weather.task.feature.data.datasource.network.service.WeatherService
import com.forecast.weather.task.feature.data.datasource.network.transformNetworkExceptions
import com.forecast.weather.task.feature.data.model.WeatherResponse
import com.forecast.weather.task.feature.data.repository.WeatherRepository
import javax.inject.Inject

// Added a separation for repository implementation,
// so if anyone wants to implement repository with volley or any other network library,
// they can do it easily, and just change it in the dependency injection.
class WeatherRepositoryImpl @Inject constructor(
    private val weatherService: WeatherService
): WeatherRepository {

    override suspend fun getWeatherByCity(
        cityName: String,
        units: String
    ): WeatherResponse {
        return transformNetworkExceptions {
            weatherService.getWeatherByCity(
                cityName = cityName
            )
        }
    }

    override suspend fun getWeatherByLocation(
        latitude: Double,
        longitude: Double,
        units: String
    ): WeatherResponse {
        return transformNetworkExceptions {
            weatherService.getWeatherByLocation(
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}
