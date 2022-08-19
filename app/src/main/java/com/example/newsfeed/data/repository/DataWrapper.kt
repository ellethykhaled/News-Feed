package com.example.newsfeed.data.repository

sealed class DataWrapper<T>(val data: T?, val message: String? = null) {

    class Success<T>(data: T) : DataWrapper<T>(data, null)
    class Loading<T>(data: T? = null) : DataWrapper<T>(data)
    class Failure<T>(message: String? = null, data: T? = null) : DataWrapper<T>(data, message)

}
