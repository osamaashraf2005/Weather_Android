package com.forecast.weather.task.feature.presentation.mapper

import com.forecast.weather.task.feature.domain.model.Weather
import com.forecast.weather.task.feature.presentation.model.UiState
import javax.inject.Inject

class WeatherMapper @Inject constructor(){

    fun map(weather: Weather): UiState {
        return UiState(
            name = weather.name,
            description = weather.description,
            icon = "https://openweathermap.org/img/wn/${weather.icon}@2x.png",
            temp = weather.temp,
            feelsLike = weather.feelsLike,
            tempMin = weather.tempMin,
            tempMax = weather.tempMax,
            pressure = weather.pressure,
            humidity = weather.humidity,
            visibility = weather.visibility,
            windSpeed = weather.windSpeed
        )
    }
}

fun Weather.mapWith(mapper: WeatherMapper): UiState {
    return mapper.map(this)
}