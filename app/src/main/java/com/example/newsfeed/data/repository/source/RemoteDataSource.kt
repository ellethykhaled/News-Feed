package com.example.newsfeed.data.repository.source

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataRepositoryInterface
import com.example.newsfeed.data.repository.payload.articles.ArticlesResponse
import com.example.newsfeed.utilis.DataWrapper
import com.example.newsfeed.data.repository.source.api.RetroInstance
import com.example.newsfeed.data.repository.source.api.RetroServiceInterface
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class RemoteDataSource(override val kodein: Kodein) : DataRepositoryInterface, KodeinAware {
    private val retroInstance: Retrofit by instance()
    private val retroService = retroInstance.create(RetroServiceInterface::class.java)

    private val call = retroService.getArticleResponse(RetroInstance.source, RetroInstance.API_KEY)

    override fun getArticles(): LiveData<DataWrapper<List<Article>>> {
        val data = MutableLiveData<DataWrapper<List<Article>>>()
        data.value = DataWrapper.Loading()

        call.clone().enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                data.value = DataWrapper.Failure(t.toString(), dataSource = DataWrapper.REMOTE)
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful)
                    data.value = DataWrapper.Success(response.body()?.articles ?: arrayListOf(), null, DataWrapper.REMOTE)
                else
                    data.value = DataWrapper.Failure(response.message(), dataSource = DataWrapper.REMOTE)
            }
        })
        return data
    }

    override fun updateArticles(articles: List<Article>?) {
        throw Exception("RemoteDataSource doesn't update articles")
    }
}