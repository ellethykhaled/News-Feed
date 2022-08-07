package com.example.newsfeed.data.api

import com.example.newsfeed.data.model.ArticlesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RetroServiceInterface {

    @GET("v1")
    fun getArticleResponse(
        @Query("source") source: String,
        @Query("apiKey") apiKey: String
    ): Call<ArticlesResponse>
}