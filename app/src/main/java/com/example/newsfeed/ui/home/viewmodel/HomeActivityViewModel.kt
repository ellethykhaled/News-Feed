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

    var toastMessage = ""
    var isOnlineBefore = false

    fun getArticlesData() {
        loadData.value = true
    }

    fun changeToastMessage(dataSource: String?) {
        toastMessage = if (dataSource == DataWrapper.LOCAL)
            "Offline Mode"
        else
            "Online Mode"
    }
}

