package com.example.newsfeed.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.example.newsfeed.ui.main.adapter.ArticleAdapter
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(R.layout.layout_home)

        val lvArticle: ListView = findViewById(R.id.lvarticle)

        val a1 = Article("Some Title", "Author", "12-2-2000", "")

        val articleList = ArrayList<Article>()

        articleList.add(a1)
        articleList.add(a1)
        articleList.add(a1)

        val articleAdapter = ArticleAdapter(this, articleList)

        lvArticle.adapter = articleAdapter

        lvArticle.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val intent = Intent(this, DetailsActivity::class.java)
            //intent.putExtra()
            startActivity(intent)
        }

    }
}