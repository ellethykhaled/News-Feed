package com.example.newsfeed.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.api.RetroInstance
import com.example.newsfeed.data.repository.api.RetroServiceInterface
import com.example.newsfeed.data.repository.database.Database
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepo(override val kodein: Kodein) : KodeinAware {

    val database = Database(kodein)

    fun makeAPICall(liveData: MutableLiveData<List<Article>>) {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)

        val call = retroService.getArticleResponse(RetroInstance.source, RetroInstance.API_KEY)

        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("Error", t.toString())
                liveData.postValue(database.query())
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body()?.articles)
                    //suspend { database.update(response.body()?.articles) }

                } else
                    liveData.postValue(database.query())
            }
        })
    }
}