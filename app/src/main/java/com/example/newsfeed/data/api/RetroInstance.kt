package com.example.newsfeed.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        private const val BASE_URL = "https://newsapi.org"
        const val API_KEY = "533af958594143758318137469b41ba9"
        const val source = "the-next-web"

        fun getRetroInstance(): Retrofit {
            return Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }
}