package com.example.newsfeed.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        private const val BASE_URL =
            "https://newsapi.org/v1/articles?source=the-next-web&apiKey=533af958594143758318137469b41ba9"

        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder().baseUrl("https://newsapi.org")
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}