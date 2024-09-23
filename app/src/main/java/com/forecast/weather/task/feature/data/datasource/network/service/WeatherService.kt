package com.forecast.weather.task.feature.data.datasource.network.service

import com.forecast.weather.task.feature.data.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {

    @GET("weather")
    suspend fun getWeatherByCity(
        @Query("q") cityName: String,
        @Query("appid") key: String = apiKey
    ): WeatherResponse

    @GET("weather")
    suspend fun getWeatherByLocation(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") key: String = apiKey
    ): WeatherResponse

    companion object {
        const val apiKey = "3c52f35f5cd60c9b7f65be7b49b0c477"
    }
}
