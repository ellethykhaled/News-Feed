package com.example.newsfeed.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.source.LocalDataSource
import com.example.newsfeed.data.repository.source.RemoteDataSource
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class DataRepo(override val kodein: Kodein) : KodeinAware {

    private val localDataSource = LocalDataSource(kodein)

    private val remoteDataSource = RemoteDataSource(kodein)

    fun getArticles(): LiveData<DataWrapper<List<Article>>> {
        val mediatorLiveData = MediatorLiveData<DataWrapper<List<Article>>>()

        mediatorLiveData.value = DataWrapper.Loading()

        val remoteArticles = remoteDataSource.getArticles()
        val localArticles = localDataSource.getArticles()

        mediatorLiveData.addSource(remoteArticles) {
            manageData(remoteArticles.value ?: DataWrapper.Failure())?.let {
                mediatorLiveData.value = it
                if (it.data != null)
                    localDataSource.updateArticles(it.data)
            }
        }
        mediatorLiveData.addSource(localArticles) {
            manageData(localArticles.value ?: DataWrapper.Failure())?.let {
                mediatorLiveData.value = it
            }
        }
        return mediatorLiveData
    }

    private fun manageData(incomingData: DataWrapper<List<Article>>): DataWrapper<List<Article>>? {
        return when (incomingData) {
            is DataWrapper.Loading -> null
            is DataWrapper.Failure -> DataWrapper.Failure(incomingData.message)
            is DataWrapper.Success -> {
                val articles = ArrayList<Article>()
                articles.addAll(incomingData.data ?: arrayListOf())
                DataWrapper.Success(articles, incomingData.message)
            }
        }
    }
}