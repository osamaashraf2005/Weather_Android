package com.forecast.weather.task.feature.data.datasource.network

import android.util.Log
import retrofit2.HttpException
import java.io.EOFException
import java.io.IOException
import kotlin.coroutines.cancellation.CancellationException

// Note that this will not deal with [CancellationException] intentionally in order
// not to conflict with the intended mechanics.
@Suppress("ThrowsCount")
suspend fun <T> transformNetworkExceptions(action: suspend () -> T): T {
    return try {
        action()
    } catch (e: HttpException) {
        Log.d("ExceptionTransformations", "HttpException: ${e.message()}")
        throw e
    } catch (e: EOFException) {
        Log.d("ExceptionTransformations", "EOFException: ${e.message}")
        throw e
    } catch (e: IOException) {
        Log.d("ExceptionTransformations", "IOException: ${e.message}")
        throw e
    } catch (e: CancellationException) {
        // Re-throw exception so that it could be handled in the upper layers
        Log.d("ExceptionTransformations", "CancellationException: ${e.message}")
        throw e
    } catch (t: Throwable) {
        throw t
    }
}
