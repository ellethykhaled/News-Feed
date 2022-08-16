package com.example.newsfeed.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.io.Serializable

class Article(
    val author: String,
    val title: String,
    val description: String,
    @PrimaryKey
    val url: String,
    val urlToImage: String,
    var publishedAt: String
) : Serializable, RealmObject
