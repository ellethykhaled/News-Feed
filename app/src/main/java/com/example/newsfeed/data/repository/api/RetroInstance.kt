package com.example.newsfeed.data.repository.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        const val BASE_URL = "https://newsapi.org/v1/"
        const val API_KEY = "533af958594143758318137469b41ba9"
        const val source = "the-next-web"

        fun getRetroInstance(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val footballDataClient = OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder().client(footballDataClient).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()

        }
    }

}