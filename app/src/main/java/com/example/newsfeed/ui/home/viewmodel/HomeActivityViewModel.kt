package com.example.newsfeed.ui.home.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.api.RetroInstance
import com.example.newsfeed.data.api.RetroServiceInterface
import com.example.newsfeed.data.model.Article
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivityViewModel: ViewModel() {

    var liveData: MutableLiveData<List<Article>> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<Article>> {
        return liveData
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)

        val call = retroService.getArticleList()

        call.enqueue(object : Callback<List<Article>> {
            override fun onFailure(call: Call<List<Article>>, t: Throwable) {
                liveData.postValue(null)
            }

            override fun onResponse(call: Call<List<Article>>, response: Response<List<Article>>) {
                liveData.postValue(response.body())
            }
        })
    }
}