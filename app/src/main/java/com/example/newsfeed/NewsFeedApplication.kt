package com.example.newsfeed

import android.app.Application
import com.example.newsfeed.data.repository.DataRepo
import com.example.newsfeed.data.repository.source.database.ArticleDatabase
import com.example.newsfeed.ui.home.HomeViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NewsFeedApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsFeedApplication))

        bind<DataRepo>() with singleton { DataRepo(kodein) }

        bind<ArticleDatabase>() with singleton { ArticleDatabase.getDatabase(this@NewsFeedApplication) }

        bind() from provider { HomeViewModelProviderFactory(kodein) }
    }
}