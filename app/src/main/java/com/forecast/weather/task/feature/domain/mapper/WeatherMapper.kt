package com.forecast.weather.task.feature.domain.mapper

import com.forecast.weather.task.feature.data.model.WeatherResponse
import com.forecast.weather.task.feature.domain.model.Weather
import javax.inject.Inject

class WeatherMapper @Inject constructor() {

    fun map(response: WeatherResponse): Weather {
        return Weather(
            name = response.name,
            description = response.weather.firstOrNull()?.description ?: "No description",
            icon = response.weather.firstOrNull()?.icon ?: "01d",  // default to a generic icon
            temp = response.main.temp,
            feelsLike = response.main.feelsLike,
            tempMin = response.main.tempMin,
            tempMax = response.main.tempMax,
            pressure = response.main.pressure,
            humidity = response.main.humidity,
            visibility = response.visibility,
            windSpeed = response.wind.speed
        )
    }
}

fun WeatherResponse.mapWith(mapper: WeatherMapper): Weather {
    return mapper.map(this)
}
