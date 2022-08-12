package com.example.newsfeed.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsfeed.ui.home.viewmodel.HomeActivityViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class HomeViewModelProviderFactory(override val kodein: Kodein): ViewModelProvider.NewInstanceFactory(), KodeinAware {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return HomeActivityViewModel() as T
    }
}