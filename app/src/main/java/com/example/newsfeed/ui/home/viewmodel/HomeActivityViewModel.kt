package com.example.newsfeed.ui.home.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataRepo
import com.example.newsfeed.data.repository.DataWrapper
import com.example.newsfeed.ui.home.adapter.ArticleAdapter
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import androidx.lifecycle.LiveData

class HomeActivityViewModel(override val kodein: Kodein, dataRepo: DataRepo) : ViewModel(), KodeinAware {

    private val loadData: MutableLiveData<Boolean> = MutableLiveData()

    val liveData: LiveData<DataWrapper<List<Article>>> = Transformations.switchMap(loadData) {
        dataRepo.getArticles()
    }

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


    fun getArticlesData() {
        loadData.value = true
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

