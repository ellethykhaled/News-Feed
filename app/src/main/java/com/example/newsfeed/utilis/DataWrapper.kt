package com.example.newsfeed.utilis

sealed class DataWrapper<out T>(val data: T?, val message: String? = null) {

    class Success<T>(data: T, message: String?, dataSource: String) : DataWrapper<T>(data, message)
    class Loading<T>(data: T? = null) : DataWrapper<T>(data)
    class Failure<T>(message: String? = null, data: T? = null) : DataWrapper<T>(data, message)

    companion object {
        const val REMOTE_SUCCESS = "Remote"
        const val LOCAL_SUCCESS = "Local"
    }
}
