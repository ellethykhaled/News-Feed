package com.example.newsfeed.common

import android.app.Application
import com.example.newsfeed.data.repository.DataRepository
import com.example.newsfeed.data.repository.source.api.RetroInstance
import com.example.newsfeed.data.repository.source.database.ArticleDatabase
import com.example.newsfeed.ui.home.HomeViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

class NewsFeedApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsFeedApplication))

        bind<DataRepository>() with singleton { DataRepository(kodein) }

        bind<ArticleDatabase>() with singleton { ArticleDatabase.getDatabase(this@NewsFeedApplication) }

        bind<Retrofit>() with provider { RetroInstance.getRetroInstance() }

        bind() from provider { HomeViewModelProviderFactory(kodein) }
    }
}