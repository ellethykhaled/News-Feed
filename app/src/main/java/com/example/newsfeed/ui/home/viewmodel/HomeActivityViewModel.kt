package com.example.newsfeed.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataRepository
import com.example.newsfeed.utilis.DataWrapper
import com.example.newsfeed.ui.home.view.adapter.ArticleRecyclerViewAdapter
import androidx.lifecycle.LiveData

class HomeActivityViewModel(dataRepository: DataRepository) : ViewModel() {

    private val loadData: MutableLiveData<Boolean> = MutableLiveData()

    val liveData: LiveData<DataWrapper<List<Article>>> = Transformations.switchMap(loadData) {
        dataRepository.getArticles()
    }

    lateinit var articleRecyclerViewAdapter: ArticleRecyclerViewAdapter

    @JvmName("setArticleAdapter1")
    fun setArticleAdapter(recyclerViewAdapter: ArticleRecyclerViewAdapter) {
        articleRecyclerViewAdapter = recyclerViewAdapter
    }

    fun getArticlesData() {
        loadData.value = true
    }
}

