package com.example.newsfeed.ui.details.viewmodel

import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataRepository

class DetailsActivityViewModel(private val dataRepository: DataRepository) : ViewModel() {

    fun getSpecificArticle(position: Int): Article? {
        return dataRepository.cachedData.cachedArticles?.get(position)
    }
}