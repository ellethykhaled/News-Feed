package com.example.newsfeed.data.repository.source.api

import com.example.newsfeed.data.repository.payload.articles.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInterface {

    @GET("articles")
    fun getArticleResponse(
        @Query("source") source: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticlesResponse>
}