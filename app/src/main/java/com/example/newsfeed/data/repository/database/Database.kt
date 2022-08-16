package com.example.newsfeed.data.repository.database

import com.example.newsfeed.data.model.Article
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

class Database(override val kodein: Kodein) : KodeinAware {

    private val configuration = RealmConfiguration.create(schema = setOf(Article::class))

    private val realm = Realm.open(configuration)

    fun write(articles: List<Article>?) {
        realm.writeBlocking {
            articles?.forEach {
                copyToRealm(it)
            }
        }
    }

    suspend fun writeAsync(articles: List<Article>) {
        realm.write {
            articles.forEach {
                copyToRealm(it)
            }
        }
    }

    suspend fun update(articles: List<Article>?) {
        realm.write {
            val query = realm.query<Article>()
            delete(query)
            articles?.forEach {
                copyToRealm(it)
            }
        }
    }

    fun query(): List<Article>? {
        return realm.query<Article>().find()
    }
}