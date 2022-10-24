package com.example.newsfeed.common

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.example.newsfeed.data.repository.CachedData
import com.example.newsfeed.data.repository.DataRepository
import com.example.newsfeed.data.repository.source.api.RetroInstance
import com.example.newsfeed.data.repository.source.database.ArticleDatabase
import com.example.newsfeed.ui.home.ViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton
import retrofit2.Retrofit

class NewsFeedApplication : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override val kodein = Kodein.lazy {
        import(androidXModule(this@NewsFeedApplication))

        bind<DataRepository>() with singleton { DataRepository(kodein) }

        bind<ArticleDatabase>() with singleton { ArticleDatabase.getDatabase(this@NewsFeedApplication) }

        bind<CachedData>() with singleton { CachedData() }

        bind<Retrofit>() with provider { RetroInstance.getRetroInstance() }

        bind() from provider { ViewModelProviderFactory(kodein) }
    }
}