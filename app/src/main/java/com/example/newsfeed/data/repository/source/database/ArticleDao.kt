package com.example.newsfeed.data.repository.source.database

import androidx.room.*
import com.example.newsfeed.data.model.Article

@Dao
interface ArticleDao {

    @Query("SELECT * FROM article_table ORDER BY publishedAt DESC")
    fun getArticlesFromDB(): List<Article>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticle(article: Article)

    @Delete
    fun deleteArticle(article: Article?)
}