package com.karvinok.data.common

import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

inline fun <R> proceedResponse(requestBlock: () -> Either.Success<R>): Either<BaseError, R> {
    return try {
        requestBlock.invoke()
    } catch (e: Exception) {
        Timber.e("Proceed Retrofit Exception: $e")
        return when (e) {
            is HttpException -> Either.Failure(
                when (e.code()) {
                    400, 401, 500 -> BaseError.ServerError(e.code(), e.buildErrorMessage())
                    else -> BaseError.NetworkError(code = e.code(),
                        message = e.buildErrorMessage())
                }
            )
            is UnknownHostException,
            is ConnectException,
            is SocketTimeoutException -> Either.Failure(BaseError.NetworkError(message = e.localizedMessage))
            else -> Either.Failure(BaseError.Error(e.localizedMessage ?: e.toString()))
        }
    }
}

fun HttpException.buildErrorMessage(): String {
    return try {
        val jsonObj = JSONObject(response()?.errorBody()?.string()?: return "error response is null")
        jsonObj.getString("error")
    } catch (e: Exception) {
        e.printStackTrace()
        e.message ?: ""
    }
}