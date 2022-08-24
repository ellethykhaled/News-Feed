package com.example.newsfeed.ui.details.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.newsfeed.R
import com.example.newsfeed.common.BasicActivity
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.ActivityDetailsBinding
import com.example.newsfeed.ui.details.viewmodel.DetailsActivityViewModel
import com.example.newsfeed.ui.home.ViewModelProviderFactory
import org.kodein.di.generic.instance

class DetailsActivity : BasicActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private lateinit var viewModel: DetailsActivityViewModel

    private lateinit var chosenArticle: Article

    companion object {
        const val ARTICLE_POSITION = "articleURL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        initViewModel()
        initUI()
    }

    private fun initViewModel() {
        val viewModelProviderFactory: ViewModelProviderFactory by kodein.instance()
        viewModel =
            ViewModelProviders.of(this, viewModelProviderFactory)[DetailsActivityViewModel::class.java]

    }

    private fun initUI() {
        val chosenArticlePosition = intent.getIntExtra(ARTICLE_POSITION, -1)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        chosenArticle = viewModel.getSpecificArticle(chosenArticlePosition)!!

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