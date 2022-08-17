package com.example.newsfeed.data.repository.database

import androidx.lifecycle.MutableLiveData
import androidx.room.*
import com.example.newsfeed.data.model.Article

@Dao
interface ArticleDao {

    @Insert
    suspend fun updateArticles(articles: List<Article>?)

    @Query("SELECT * FROM article_table ORDER BY publishedAt DESC")
    fun getArticlesFromDB(): List<Article>?

    @Insert
    fun insertArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article?)
}