package com.test.weather

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.forecast.weather.task.feature.domain.model.Weather
import com.forecast.weather.task.feature.domain.usecase.FetchWeatherUseCase
import com.forecast.weather.task.feature.presentation.viewmodel.WeatherViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var fetchWeatherUseCase: FetchWeatherUseCase

    @MockK
    lateinit var context: Context

    lateinit var viewModel: WeatherViewModel
    lateinit var sharedPreferences: SharedPreferences

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        sharedPreferences = mockk(relaxed = true)
        every { context.getSharedPreferences(any(), any()) } returns sharedPreferences

        viewModel = WeatherViewModel(fetchWeatherUseCase, context)
    }

    @Test
    fun `fetch weather by city updates weather data`() = runTest {
        // Given a mock weather object
        val weather = Weather("Test City", "Clear", "01d", 25.0, 23.0, 20.0, 30.0, 1010, 80, 10000, 5.0)
        coEvery { fetchWeatherUseCase.getWeather(any()) } returns weather

        // When fetching weather by city
        viewModel.fetchWeatherByCity("Test City")

        // Then the LiveData should be updated
        assertEquals(weather, viewModel.weatherData.value)
    }

    @Test
    fun `save last searched city`() = runTest {
        // Given
        val city = "Test City"

        // When fetching weather by city
        viewModel.fetchWeatherByCity(city)

        // Then
        every { sharedPreferences.edit().putString("last_city", city).apply() } returns Unit
    }

    @Test
    fun `get last searched city`() {
        // Given a saved city
        every { sharedPreferences.getString("last_city", null) } returns "Test City"

        // When fetching last city
        val result = viewModel.getLastSearchedCity()

        // Then the result should be the last saved city
        assertEquals("Test City", result)
    }
}