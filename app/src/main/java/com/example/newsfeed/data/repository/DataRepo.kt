package com.example.newsfeed.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.api.RetroInstance
import com.example.newsfeed.data.repository.api.RetroServiceInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class DataRepo {

    fun makeAPICall(liveData: MutableLiveData<List<Article>>) {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)

        val call = retroService.getArticleResponse(RetroInstance.source, RetroInstance.API_KEY)

        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("Error", t.toString())
                liveData.postValue(null)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    formatDate(response.body()?.articles!!)

                    liveData.postValue(response.body()?.articles)
                } else
                    liveData.postValue(null)
            }
        })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(articles: List<Article>) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date: LocalDate
        for (article in articles) {
            date = LocalDate.parse(article.publishedAt, formatter)
            Log.e("Date", date.month.toString())
            article.publishedAt =
                date.month.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " " + date.dayOfMonth + ", " + date.year
        }
    }
}