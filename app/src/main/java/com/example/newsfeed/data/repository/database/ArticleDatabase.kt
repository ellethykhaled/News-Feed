package com.example.newsfeed.data.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.newsfeed.data.model.Article
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class ArticleDatabase() : RoomDatabase() {

    abstract fun articleDao(): ArticleDao

    companion object {
        @Volatile
        private var INSTANCE: ArticleDatabase? = null

        fun getDatabase(context: Context): ArticleDatabase? {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context,
                    ArticleDatabase::class.java,
                    "article_database"
                ).build()
            }
        }
    }
}