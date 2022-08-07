package com.example.newsfeed.data.api

import com.example.newsfeed.data.model.Article
import retrofit2.Call
import retrofit2.http.GET

interface RetroServiceInterface {

    @GET("v1")
    fun getArticleList(): Call<List<Article>>
}