package com.forecast.weather.task.feature.domain.usecase

import com.forecast.weather.task.feature.data.repository.WeatherRepository
import com.forecast.weather.task.feature.domain.mapper.WeatherMapper
import com.forecast.weather.task.feature.domain.mapper.mapWith
import com.forecast.weather.task.feature.domain.model.Weather
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val weatherMapper: WeatherMapper
// We can also add error handler here for handling the errors, or we can add a super UserCase
// class which handle the onException when something goes wrong with execute function
//  @Inject
// private val errorHandler: ErrorHandler
) {

    suspend fun getWeather(query: Query): Weather {

        return when(query) {
            is Query.ByCityName -> weatherRepository.getWeatherByCity(query.cityName)
            is Query.ByCoordinates -> weatherRepository.getWeatherByLocation(query.latitude, query.longitude)
        }.mapWith(
            mapper = weatherMapper
        )
    }

    sealed interface Query {
        data class ByCityName(
            val cityName: String
        ) : Query

        data class ByCoordinates(
            val latitude: Double,
            val longitude: Double
        ) : Query
    }
}
