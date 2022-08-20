package com.example.newsfeed.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsfeed.data.repository.DataRepo
import com.example.newsfeed.ui.home.viewmodel.HomeActivityViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class HomeViewModelProviderFactory(override val kodein: Kodein): ViewModelProvider.NewInstanceFactory(), KodeinAware {

    private val dataRepo: DataRepo by instance()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeActivityViewModel(kodein, dataRepo) as T
    }
}