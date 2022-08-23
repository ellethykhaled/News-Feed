package com.example.newsfeed.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataRepository
import com.example.newsfeed.utilis.DataWrapper
import androidx.lifecycle.LiveData

class HomeActivityViewModel(dataRepository: DataRepository) : ViewModel() {

    private val loadData: MutableLiveData<Boolean> = MutableLiveData()

    val liveData: LiveData<DataWrapper<List<Article>>> = Transformations.switchMap(loadData) {
        dataRepository.getArticles()
    }

    var isRemote = false
    var isLocal = false
    var firstConnection = true

    fun getArticlesData() {
        loadData.value = true
    }

    companion object {
        const val OFFLINE_MESSAGE = "Offline Mode"
        const val ONLINE_MESSAGE = "Online Mode"
    }
}

