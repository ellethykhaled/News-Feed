package com.example.newsfeed.data.model

import java.io.Serializable

class Article(
    val title: String,
    val author: String,
    val date: String,
    val description: String,
    val image: String
) : Serializable
