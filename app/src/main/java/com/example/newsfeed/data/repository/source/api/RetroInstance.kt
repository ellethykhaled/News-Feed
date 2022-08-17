package com.example.newsfeed.data.repository.source.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetroInstance {

    companion object {
        const val BASE_URL = "https://newsapi.org/v1/"
        const val API_KEY = "5b9dbdd78c834dc2a5e61584c271b780"
        const val source = "the-next-web"

        fun getRetroInstance(): Retrofit {
            val loggingInterceptor = HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)

            val dataClient = OkHttpClient().newBuilder()
                .addInterceptor(loggingInterceptor)
                .build()

            return Retrofit.Builder().client(dataClient).baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build()
        }
    }

}