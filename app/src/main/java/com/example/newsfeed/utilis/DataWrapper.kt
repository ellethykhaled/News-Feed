package com.example.newsfeed.utilis

sealed class DataWrapper<out T>(val data: T?, val message: String? = null, val dataSource: String? = null) {

    class Success<T>(data: T, message: String?, dataSource: String) : DataWrapper<T>(data, message, dataSource)
    class Loading<T>(data: T? = null) : DataWrapper<T>(data)
    class Failure<T>(message: String? = null, data: T? = null, dataSource: String?) : DataWrapper<T>(data, message, dataSource)

    companion object {
        const val REMOTE = "Remote"
        const val LOCAL = "Local"
    }
}
