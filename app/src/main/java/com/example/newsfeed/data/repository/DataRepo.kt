package com.example.newsfeed.data.repository

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.api.RetroInstance
import com.example.newsfeed.data.repository.api.RetroServiceInterface
import com.example.newsfeed.data.repository.database.ArticleDao
import com.example.newsfeed.data.repository.database.ArticleDatabase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataRepo(override val kodein: Kodein, val context: Context) : KodeinAware, ArticleDao {


    fun getArticles(liveData: MutableLiveData<List<Article>>) {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)

        val call = retroService.getArticleResponse(RetroInstance.source, RetroInstance.API_KEY)

        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("Error", t.toString())
                liveData.postValue(getArticlesFromDB())
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    liveData.postValue(response.body()?.articles)
                    suspend { updateArticles(response.body()?.articles) }

                } else
                    liveData.postValue(getArticlesFromDB())
            }
        })
    }

    override suspend fun updateArticles(articles: List<Article>?) {
        getArticlesFromDB()?.forEach {
            deleteArticle(it)
        }
        val articleDao = ArticleDatabase.getDatabase(context.applicationContext)?.articleDao()
        articles?.forEach {
            articleDao?.insertArticle(it)
        }
    }

    override fun getArticlesFromDB(): List<Article>? {
        val articleDao = ArticleDatabase.getDatabase(context.applicationContext)?.articleDao()
        return articleDao?.getArticlesFromDB()!!
    }

    override fun insertArticle(article: Article) {
        TODO("Not yet implemented")
    }

    override fun deleteArticle(article: Article?) {
        TODO("Not yet implemented")
    }
}