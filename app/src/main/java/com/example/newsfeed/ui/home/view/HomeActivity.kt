package com.example.newsfeed.ui.home.view

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.GONE
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataRepo
import com.example.newsfeed.databinding.ActivityHomeBinding
import com.example.newsfeed.ui.details.view.DetailsActivity
import com.example.newsfeed.ui.home.HomeViewModelProviderFactory
import com.example.newsfeed.ui.home.adapter.ArticleAdapter
import com.example.newsfeed.ui.home.viewmodel.HomeActivityViewModel
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class HomeActivity : AppCompatActivity(), ArticleAdapter.Callback, KodeinAware {

    override val kodein by kodein()

    private lateinit var binding: ActivityHomeBinding
    private lateinit var manager: RecyclerView.LayoutManager

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        manager = LinearLayoutManager(this)

        binding.recyclerViewArticle.apply {
            adapter = ArticleAdapter(emptyList(), this@HomeActivity)

            layoutManager = manager
        }

        binding.recyclerViewArticle.addItemDecoration(HomeListItemDecorator(20))

        initViewModel()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViewModel() {
        val viewModelProviderFactory: HomeViewModelProviderFactory by kodein.instance()

        val viewModel = ViewModelProviders.of(this, viewModelProviderFactory)
            .get(HomeActivityViewModel::class.java)

        val dataRepo = DataRepo(kodein)
        dataRepo.getArticles(viewModel.getLiveDataObserver())

        viewModel.setArticleAdapter(binding.recyclerViewArticle.adapter as ArticleAdapter)

        viewModel.getLiveDataObserver().observe(this, Observer {
            binding.apiResponseProgressBar.visibility = GONE
            if (it != null)
                viewModel.setArticleAdapterData(it)
            else
                Toast.makeText(this, "Error Loading Data", Toast.LENGTH_SHORT).show()
        })
    }


    private fun openDetailsActivity(item: Article) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.ARTICLE_DATA, item)
        startActivity(intent)
    }

    override fun onArticleClick(item: Article) {
        openDetailsActivity(item)
    }

    //Just used for testing
    private fun createDummyArticles(viewModel: HomeActivityViewModel) {
        val article = Article(
            "Khaled",
            "Test Article",
            "Some description, Some description, Some description, Some description, Some description, Some description, Some description, Some description, Some description, Some description, Some description.",
            "www.google.com",
            "",
            "Some Date"
        )

        viewModel.liveData.value = listOf(article, article, article, article, article)
    }
}