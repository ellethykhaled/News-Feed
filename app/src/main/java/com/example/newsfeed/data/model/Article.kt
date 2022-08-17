package com.example.newsfeed.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "article_table")
class Article(
    val author: String,
    val title: String,
    val description: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String,
    var publishedAt: String
) : Serializable
