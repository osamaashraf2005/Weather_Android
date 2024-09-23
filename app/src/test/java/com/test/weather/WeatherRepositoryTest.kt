package com.test.weather

import com.forecast.weather.task.feature.data.datasource.network.repository.WeatherRepositoryImpl
import com.forecast.weather.task.feature.data.datasource.network.service.WeatherService
import com.forecast.weather.task.feature.data.model.Main
import com.forecast.weather.task.feature.data.model.WeatherResponse
import com.forecast.weather.task.feature.data.model.Wind
import com.forecast.weather.task.feature.domain.mapper.WeatherMapper
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherRepositoryTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    lateinit var weatherApiService: WeatherService

    private lateinit var weatherRepository: WeatherRepositoryImpl

    @MockK
    lateinit var weatherMapper: WeatherMapper

    @Before
    fun setUp() {
        weatherRepository = WeatherRepositoryImpl(weatherApiService)
    }

    @Test
    fun `get weather by city returns mapped Weather object`() = runTest {
        // Given a mock response from the API
        val weatherResponse = WeatherResponse(
            weather = listOf(WeatherResponse.Weather(description = "Clear", icon = "01d")),
            main = WeatherResponse.Main(temp = 25.0, feelsLike = 24.0, tempMin = 22.0, tempMax = 28.0, pressure = 1010, humidity = 80, seaLevel = null, grndLevel = null),
            visibility = 10000,
            wind = WeatherResponse.Wind(speed = 5.0, deg = 200, gust = 6.0),
            name = "Test City"
        )
        coEvery { weatherApiService.getWeatherByLocation(any(), any(), any()) } returns weatherResponse

        // Mock the mapper's behavior
        val mappedWeather = Weather(
            name = "Test City",
            description = "Clear",
            icon = "01d",
            temp = 25.0,
            feelsLike = 24.0,
            tempMin = 22.0,
            tempMax = 28.0,
            pressure = 1010,
            humidity = 80,
            visibility = 10000,
            windSpeed = 5.0
        )
        coEvery { weatherMapper.map(weatherResponse) } returns mappedWeather

        // When fetching weather by city name
        val result = weatherRepository.getWeatherByCity("Test City")

        // Then the result should be the mapped Weather object
        assertEquals(mappedWeather, result)
    }

    @Test
    fun `get weather by location returns mapped Weather object`() = runBlocking {
        Weather(description = "Cloudy", icon = "02d",)
        // Given a mock response from the API
        val weatherResponse = WeatherResponse(
            weather = listOf(),
            main = Main(temp = 20.0, feelsLike = 19.0, tempMin = 18.0, tempMax = 22.0, pressure = 1012, humidity = 85, seaLevel = null, grndLevel = null),
            visibility = 8000,
            wind = Wind(speed = 4.0, deg = 220, gust = 5.0),
            name = "Another City"
        )
        coEvery { weatherApiService.getWeatherByLocation(any(), any(), any()) } returns weatherResponse

        // Mock the mapper's behavior
        val mappedWeather = Weather(
            name = "Another City",
            description = "Cloudy",
            icon = "02d",
            temp = 20.0,
            feelsLike = 19.0,
            tempMin = 18.0,
            tempMax = 22.0,
            pressure = 1012,
            humidity = 85,
            visibility = 8000,
            windSpeed = 4.0
        )
        coEvery { weatherMapper.map(weatherResponse) } returns mappedWeather

        // When fetching weather by location
        val result = weatherRepository.getWeatherByLocation(44.34, 10.99)

        // Then the result should be the mapped Weather object
        assertEquals(mappedWeather, result)
    }
}