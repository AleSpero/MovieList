package com.sperolabs.movielist.model

sealed class DataResult<T>(data: T?, message: String? = null) {
    class Success<T>(val response: T) : DataResult<T>(response)
    class Failure<T>(val error: Throwable) : DataResult<T>(data = null, message = error.localizedMessage)
    class Empty<T> : DataResult<T>(null)
}