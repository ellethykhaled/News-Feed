package com.example.newsfeed.data.repository

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.source.api.RetroInstance
import com.example.newsfeed.data.repository.source.api.RetroServiceInterface
import com.example.newsfeed.data.repository.source.LocalDataSource
import com.example.newsfeed.data.repository.source.RemoteDataSource
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepo(override val kodein: Kodein) : KodeinAware {

    private val localDataSource = LocalDataSource(kodein)

    private val remoteDataSource = RemoteDataSource(kodein)

    fun getArticles(liveData: MutableLiveData<List<Article>>) {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)

        val call = retroService.getArticleResponse(RetroInstance.source, RetroInstance.API_KEY)

        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("Error", t.toString())
                liveData.postValue(localDataSource.getArticles().value?.data)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body()?.articles)
                    localDataSource.updateArticles(response.body()?.articles)

                } else
                    liveData.postValue(localDataSource.getArticles().value?.data)
            }
        })
    }
}