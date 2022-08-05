package com.example.newsfeed.ui.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.ActivityHomeBinding
import com.example.newsfeed.ui.details.view.DetailsActivity
import com.example.newsfeed.ui.home.adapter.ArticleAdapter

class HomeActivity : AppCompatActivity(), ArticleAdapter.Callback {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val a1 = Article("Some Title", "Author", "12-2-2000", "", "")

        val articles = listOf(a1,a1,a1)

        manager = LinearLayoutManager(this)

        binding.recyclerViewArticle.apply {
            adapter = ArticleAdapter(articles, this@HomeActivity)

            layoutManager = manager
        }
    }

    private fun openDetailsActivity(item: Article) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.ARTICLE_DATA, item)
        startActivity(intent)
    }

    override fun onArticleClick(item: Article) {
        openDetailsActivity(item)
    }
}