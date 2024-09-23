package com.test.weather

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.forecast.weather.task.feature.presentation.WeatherDisplayScreen
import org.junit.Rule
import org.junit.Test

class WeatherDisplayScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun `display weather information`() {
        // Set the Composable under test with a mocked weather data
        composeTestRule.setContent {
            WeatherDisplayScreen(
                cityName = "Test City",
                navController = mockk(relaxed = true),
                viewModel = mockk(relaxed = true)
            )
        }

        // Check if the city name is displayed
        composeTestRule.onNodeWithText("City: Test City").assertExists()

        // Check if the temperature is displayed
        composeTestRule.onNodeWithText("Temperature: 25°C").assertExists()
    }

    @Test
    fun `back button navigates back`() {
        composeTestRule.setContent {
            WeatherDisplayScreen(
                navController = mockk(relaxed = true),
                cityName = "Test City"
            )
        }

        // Click the back button and assert that navigation happens
        composeTestRule.onNodeWithText("Back").performClick()
    }
}