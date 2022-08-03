package com.example.newsfeed.ui.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import androidx.databinding.DataBindingUtil
import com.example.newsfeed.ui.home.adapter.ArticleAdapter
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.ActivityHomeBinding
import com.example.newsfeed.ui.details.view.DetailsActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val lvArticle: ListView = findViewById(R.id.listview_article)

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