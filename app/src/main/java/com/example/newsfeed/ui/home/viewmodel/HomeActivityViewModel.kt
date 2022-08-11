package com.example.newsfeed.ui.home.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.ui.home.adapter.ArticleAdapter
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class HomeActivityViewModel : ViewModel() {

    var liveData: MutableLiveData<List<Article>> = MutableLiveData()

    lateinit var articleAdapter: ArticleAdapter

    @JvmName("setArticleAdapter1")
    fun setArticleAdapter(adapter: ArticleAdapter) {
        articleAdapter = adapter
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    fun setArticleAdapterData(data: List<Article>) {
        formatDate(data)
        articleAdapter.setArticles(data)
        articleAdapter.notifyDataSetChanged()
    }

    fun getLiveDataObserver(): MutableLiveData<List<Article>> {
        return liveData
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun formatDate(articles: List<Article>) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        var date: LocalDate
        for (article in articles) {
            try {
                date = LocalDate.parse(article.publishedAt, formatter)
                article.publishedAt =
                    date.month.toString().lowercase()
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " " + date.dayOfMonth + ", " + date.year
            } catch (e: Exception) {
            }
        }
    }
}
