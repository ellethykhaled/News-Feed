package com.example.newsfeed

import android.app.Application
import com.example.newsfeed.ui.home.HomeViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsFeedApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        //This line some how has the whole application working
        bind<Kodein>() with singleton { kodein }
        //Error given earlier: "No binding found for bind<Kodein>() with ? { ? }"

        bind() from provider { HomeViewModelProviderFactory(instance()) }
    }
}