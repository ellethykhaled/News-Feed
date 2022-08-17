package com.example.newsfeed

import android.app.Application
import android.content.Context
import com.example.newsfeed.data.repository.DataRepo
import com.example.newsfeed.data.repository.database.ArticleDao
import com.example.newsfeed.ui.home.HomeViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsFeedApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {

        bind() from provider { HomeViewModelProviderFactory(kodein) }
    }
}