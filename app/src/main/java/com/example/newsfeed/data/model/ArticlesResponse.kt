package com.example.newsfeed.data.model

import com.google.gson.Gson
import java.lang.IllegalStateException

class ArticlesResponse {
    var status: String? = null
    var source: String? = null
    var sortBy  : String? = null
    var articles: ArrayList<Article>? = null

    companion object {
        fun createInstance(jsonString: String): ArticlesResponse? {
            return try {
                Gson().fromJson(jsonString, ArticlesResponse::class.java)
            }
            catch (exception: IllegalStateException) {
                exception.printStackTrace()
                null
            }
        }
    }
}