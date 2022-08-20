package com.example.newsfeed.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.source.LocalDataSource
import com.example.newsfeed.data.repository.source.RemoteDataSource
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class DataRepo(override val kodein: Kodein) : KodeinAware {

    private val localDataSource = LocalDataSource(kodein)

    private val remoteDataSource = RemoteDataSource(kodein)

    fun getArticles(): LiveData<DataWrapper<List<Article>>> {
        val data = MediatorLiveData<DataWrapper<List<Article>>>()

        data.value = DataWrapper.Loading()

        val remoteArticles = remoteDataSource.getArticles()

        data.addSource(remoteArticles) {
            manageData(data.value ?: DataWrapper.Failure())?.let {
                data.value = it
            }
        }
        return data
    }

    private fun manageData(incomingData: DataWrapper<List<Article>>): DataWrapper<List<Article>>? {
        if (incomingData is DataWrapper.Loading)
            return null

        val articles = ArrayList<Article>()

        when (incomingData) {
            is DataWrapper.Success -> articles.addAll(incomingData.data ?: arrayListOf())
            is DataWrapper.Failure -> return DataWrapper.Failure(incomingData.message)
            else -> {}
        }

        return DataWrapper.Success(articles)
    }
}