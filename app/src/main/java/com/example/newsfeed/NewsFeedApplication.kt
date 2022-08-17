package com.example.newsfeed

import android.app.Application
import com.example.newsfeed.data.repository.DataRepo
import com.example.newsfeed.data.repository.database.Database
import com.example.newsfeed.ui.home.HomeViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsFeedApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {

        bind() from singleton { Database(kodein) }

        bind() from singleton { DataRepo(kodein) }

        bind() from provider { HomeViewModelProviderFactory(kodein) }
    }
}