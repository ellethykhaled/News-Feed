package com.example.newsfeed.ui.home.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.ui.home.adapter.ArticleAdapter

class HomeActivityViewModel : ViewModel() {

    var liveData: MutableLiveData<List<Article>> = MutableLiveData()

    lateinit var articleAdapter: ArticleAdapter

    @JvmName("setArticleAdapter1")
    fun setArticleAdapter(adapter: ArticleAdapter) {
        articleAdapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setArticleAdapterData(data: List<Article>) {
        articleAdapter.setArticles(data)
        articleAdapter.notifyDataSetChanged()
    }

    fun getLiveDataObserver(): MutableLiveData<List<Article>> {
        return liveData
    }
}
