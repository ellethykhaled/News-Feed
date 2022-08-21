package com.example.newsfeed.utilis

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.newsfeed.data.model.Article
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(articles: List<Article>) {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    var date: LocalDate
    for (article in articles) {
        try {
            date = LocalDate.parse(article.publishedAt, formatter)
            article.publishedAt =
                date.month.toString().lowercase()
                    .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } + " " + date.dayOfMonth + ", " + date.year
        } catch (e: Exception) {
        }
    }
}