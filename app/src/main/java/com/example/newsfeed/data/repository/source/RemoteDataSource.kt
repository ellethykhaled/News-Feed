package com.example.newsfeed.data.repository.source

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.ArticlesResponse
import com.example.newsfeed.data.repository.DataWrapper
import com.example.newsfeed.data.repository.source.api.RetroInstance
import com.example.newsfeed.data.repository.source.api.RetroServiceInterface
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource(override val kodein: Kodein) : KodeinAware {
    private val retroService : RetroServiceInterface by instance()

    private val call = retroService.getArticleResponse(RetroInstance.source, RetroInstance.API_KEY)

    fun getArticles(): LiveData<DataWrapper<List<Article>>> {
        val data = MutableLiveData<DataWrapper<List<Article>>>()
        data.value = DataWrapper.Loading()

        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                data.value = DataWrapper.Failure()
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful)
                    data.value = DataWrapper.Success(response.body()?.articles ?: arrayListOf())
                else
                    data.value = DataWrapper.Failure(response.message())
            }
        })
        return data
    }
}