package com.example.newsfeed

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ListView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        supportActionBar?.hide()
        window.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_FULLSCREEN
        }
        setContentView(R.layout.home)

        val lvArticle: ListView = findViewById(R.id.lvarticle)

        val a1 = Article("Some Title", "Author", "12-2-2000")

        val articleList = ArrayList<Article>()

        articleList.add(a1)
        articleList.add(a1)
        articleList.add(a1)

        val articleAdapter = ArticleAdapter(this, articleList)

        lvArticle.adapter = articleAdapter

        lvArticle.setOnItemClickListener { adapterView: AdapterView<*>, view1: View, i: Int, l: Long ->
            val intent = Intent(this, Details::class.java)
            //intent.putExtra()
            startActivity(intent)
        }

    }
}