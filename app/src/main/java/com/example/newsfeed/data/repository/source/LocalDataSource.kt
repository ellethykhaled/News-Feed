package com.example.newsfeed.data.repository.source

import com.example.newsfeed.data.model.Article
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

    fun getArticles() : List<Article>? {
        val articleDao = database.articleDao()
        return articleDao.getArticlesFromDB()
    }
}