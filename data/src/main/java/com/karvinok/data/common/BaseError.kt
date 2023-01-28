package com.karvinok.data.common

sealed class BaseError(message: String? = null) : Exception(message) {
    class NetworkError(val code: Int? = null, message: String? = null) : BaseError()
    class ServerError(val code: Int, override val message: String? = null) : BaseError(message)
    class Error(val e: String? = null) : BaseError(message = e)
}

