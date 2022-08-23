package com.example.newsfeed.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.source.LocalDataSource
import com.example.newsfeed.data.repository.source.RemoteDataSource
import com.example.newsfeed.utilis.DataWrapper
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class DataRepository(override val kodein: Kodein) : DataRepositoryInterface, KodeinAware {

    private val localDataSource = LocalDataSource(kodein)

    private val remoteDataSource = RemoteDataSource(kodein)

    private val cachedData: CachedData by kodein.instance()

    override fun getArticles(): LiveData<DataWrapper<List<Article>>> {
        val mediatorLiveData = MediatorLiveData<DataWrapper<List<Article>>>()

        mediatorLiveData.value = DataWrapper.Loading()

        val remoteArticles = remoteDataSource.getArticles()
        val localArticles = localDataSource.getArticles()

        mediatorLiveData.addSource(remoteArticles) {
            manageData(
                remoteArticles.value ?: DataWrapper.Failure(dataSource = DataWrapper.REMOTE),
                DataWrapper.REMOTE
            )?.let {
                mediatorLiveData.value = it
                if (it.data != null) {
                    localDataSource.updateArticles(it.data)
                    cachedData.cachedArticles = it.data
                }
            }
        }
        mediatorLiveData.addSource(localArticles) {
            manageData(
                localArticles.value ?: DataWrapper.Failure(dataSource = DataWrapper.LOCAL),
                DataWrapper.LOCAL
            )?.let {
                mediatorLiveData.value = it
                cachedData.cachedArticles = it.data
            }
        }
        return mediatorLiveData
    }

    override fun updateArticles(articles: List<Article>?) {
        throw Exception("DataRepository doesn't update articles")
    }

    private fun manageData(
        incomingData: DataWrapper<List<Article>>,
        localSuccess: String
    ): DataWrapper<List<Article>>? {
        return when (incomingData) {
            is DataWrapper.Loading -> null
            is DataWrapper.Failure -> DataWrapper.Failure(message = incomingData.message, dataSource = incomingData.dataSource)
            is DataWrapper.Success -> {
                val articles = ArrayList<Article>()
                articles.addAll(incomingData.data ?: arrayListOf())
                DataWrapper.Success(articles, incomingData.message, localSuccess)
            }
        }
    }
}