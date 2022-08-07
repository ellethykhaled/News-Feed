package com.example.newsfeed.data.model

import java.io.Serializable

class Article(
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val date: String
) : Serializable
