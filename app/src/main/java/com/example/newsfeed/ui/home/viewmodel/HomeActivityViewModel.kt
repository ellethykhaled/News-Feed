package com.example.newsfeed.ui.home.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsfeed.data.repository.api.RetroInstance
import com.example.newsfeed.data.repository.api.RetroServiceInterface
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.ArticlesResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivityViewModel : ViewModel() {

    var liveData: MutableLiveData<List<Article>> = MutableLiveData()

    fun getLiveDataObserver(): MutableLiveData<List<Article>> {
        return liveData
    }

    fun makeAPICall() {
        val retroInstance = RetroInstance.getRetroInstance()
        val retroService = retroInstance.create(RetroServiceInterface::class.java)

        val call = retroService.getArticleResponse(RetroInstance.source, RetroInstance.API_KEY)

        call.enqueue(object : Callback<ArticlesResponse> {
            override fun onFailure(call: Call<ArticlesResponse>, t: Throwable) {
                Log.e("Error", t.toString())
            }

            override fun onResponse(
                call: Call<ArticlesResponse>,
                response: Response<ArticlesResponse>
            ) {
                if (response.isSuccessful) {
                    Log.e("Success", response.body().toString())
                    liveData.postValue(response.body()?.articles)
                }
            }
        })
    }
}
