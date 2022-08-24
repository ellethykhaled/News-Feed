package com.example.newsfeed.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsfeed.data.repository.DataRepository
import com.example.newsfeed.ui.details.viewmodel.DetailsActivityViewModel
import com.example.newsfeed.ui.home.viewmodel.HomeActivityViewModel
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance
import java.lang.IllegalArgumentException

class ViewModelProviderFactory(override val kodein: Kodein) :
    ViewModelProvider.NewInstanceFactory(), KodeinAware {

    private val dataRepository: DataRepository by instance()

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(HomeActivityViewModel::class.java) -> {
                return HomeActivityViewModel(dataRepository) as T
            }
            modelClass.isAssignableFrom(DetailsActivityViewModel::class.java) -> {
                return DetailsActivityViewModel(dataRepository) as T
            }
        }
        throw IllegalArgumentException("Unknown viewmodel class: " + modelClass.name)
    }
}