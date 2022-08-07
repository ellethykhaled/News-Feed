package com.example.newsfeed.ui.details.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    companion object {
        const val ARTICLE_DATA = "article"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        val chosenArticle = intent.getSerializableExtra(ARTICLE_DATA) as Article

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        binding.articleItem = chosenArticle

        binding.backButton.setOnClickListener {
            this.onBackPressed()
        }

        binding.websiteButton.setOnClickListener {
            openWebsite(chosenArticle.url)
        }
    }

    private fun openWebsite(url: String) {
        val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(browserIntent)
    }
}