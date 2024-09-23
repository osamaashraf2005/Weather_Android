package com.test.weather

import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test

class WeatherRepositoryMockWebServerTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `get weather by city from MockWebServer`() = runTest {
        // Enqueue a mock response
        val mockResponse = MockResponse()
            .setBody("""{
                "weather": [{"description": "Clear", "icon": "01d"}],
                "main": {"temp": 25.0, "feels_like": 24.0, "temp_min": 22.0, "temp_max": 28.0, "pressure": 1010, "humidity": 80},
                "visibility": 10000,
                "wind": {"speed": 5.0, "deg": 200, "gust": 6.0},
                "name": "Test City"
            }""")
            .setResponseCode(200)
        mockWebServer.enqueue(mockResponse)

        // Now perform the test using the mock server URL
        val baseUrl = mockWebServer.url("/").toString()
        // Use baseUrl to configure Retrofit in your tests

        // Assert that the response was handled correctly
    }
}