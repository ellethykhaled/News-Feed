package com.example.newsfeed.data.repository

import androidx.lifecycle.LiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.utilis.DataWrapper

interface DataRepositoryInterface {

    fun getArticles(): LiveData<DataWrapper<List<Article>>>

    fun updateArticles(articles: List<Article>?)
}