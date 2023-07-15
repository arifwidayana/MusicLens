package com.arifwidayana.musiclens.arch.base

import com.arifwidayana.musiclens.arch.utils.ext.ApiErrorException
import com.arifwidayana.musiclens.arch.utils.ext.NoInternetConnectionException
import com.arifwidayana.musiclens.arch.utils.ext.UnexpectedErrorException
import com.arifwidayana.musiclens.arch.wrapper.DataResource
import retrofit2.HttpException
import java.io.IOException

/**
 * BaseRepository created to ensure when the function hit API
 * the result can be to handle error using coroutine
 */
abstract class BaseRepository {
    abstract fun <T> getErrorMessageFromApi(response: T): String

    suspend fun <T> safeNetworkCall(apiCall: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is IOException -> DataResource.Error(NoInternetConnectionException())
                is HttpException -> {
                    DataResource.Error(
                        ApiErrorException(
                            getErrorMessageFromApi(
                                response = throwable.response()?.errorBody()
                            ),
                            httpCode = throwable.code()
                        )
                    )
                }
                else -> {
                    DataResource.Error(UnexpectedErrorException())
                }
            }
        }
    }

    suspend fun <T> proceed(coroutine: suspend () -> T): DataResource<T> {
        return try {
            DataResource.Success(coroutine.invoke())
        } catch (exception: Exception) {
            DataResource.Error(exception)
        }
    }
}