package com.example.newsfeed.data.repository.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataWrapper
import com.example.newsfeed.data.repository.source.database.ArticleDatabase
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class LocalDataSource(override val kodein: Kodein) : KodeinAware {
    private val database: ArticleDatabase by instance()

    fun updateArticles(articles: List<Article>?) {
        val articleDao = database.articleDao()
        articleDao.getArticlesFromDB()?.forEach {
            articleDao.deleteArticle(it)
        }
        articles?.forEach {
            articleDao.insertArticle(it)
        }
    }

    fun getArticles(): LiveData<DataWrapper<List<Article>>> {
        val data = MutableLiveData<DataWrapper<List<Article>>>()
        data.value = DataWrapper.Loading()

        val articleDao = database.articleDao()
        val articles = articleDao.getArticlesFromDB()

        if (articles == null)
            data.value = DataWrapper.Failure("No Local Data Found")
        else
            data.value = DataWrapper.Success(articles)

        return data
    }
}