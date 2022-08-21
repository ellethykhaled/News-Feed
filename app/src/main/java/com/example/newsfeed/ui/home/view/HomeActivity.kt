package com.example.newsfeed.ui.home.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataWrapper
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
    private lateinit var viewModel: HomeActivityViewModel
    private var initialToastMessage = ""
    private var refreshedBefore = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initUi()
        initViewModel()
        viewModel.getArticlesData()
    }

    private fun initUi() {
        supportActionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        val manager = LinearLayoutManager(this)

        binding.recyclerViewArticle.apply {
            adapter = ArticleAdapter(emptyList(), this@HomeActivity)
            layoutManager = manager
        }

        binding.recyclerViewArticle.addItemDecoration(HomeListItemDecorator(20))

        binding.refresher.setOnRefreshListener {
            viewModel.getArticlesData()
            refreshedBefore = true
        }

        loadingDelay()
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViewModel() {
        val viewModelProviderFactory: HomeViewModelProviderFactory by kodein.instance()

        viewModel =
            ViewModelProviders.of(this, viewModelProviderFactory)[HomeActivityViewModel::class.java]

        viewModel.setArticleAdapter(binding.recyclerViewArticle.adapter as ArticleAdapter)

        viewModel.liveData.observe(this) {
            onLiveDataChange(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onLiveDataChange(it: DataWrapper<List<Article>>) {
        when (it) {
            is DataWrapper.Loading -> {}
            is DataWrapper.Failure -> Toast.makeText(
                this,
                "Error Loading Data",
                Toast.LENGTH_SHORT
            ).show()
            is DataWrapper.Success -> {
                binding.refresher.isRefreshing = false
                if (it.message == DataWrapper.LOCAL_SUCCESS && refreshedBefore)
                    return
                it.data?.let {
                    bindArticlesData(it)
                }
                initialToastMessage = if (it.message == DataWrapper.LOCAL_SUCCESS)
                    "Offline Mode"
                else
                    "Online"
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    private fun bindArticlesData(articleList: List<Article>) {
        val adapter = binding.recyclerViewArticle.adapter as? ArticleAdapter
        adapter?.setArticles(articleList)
    }

    private fun openDetailsActivity(item: Article) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.ARTICLE_DATA, item)
        startActivity(intent)
    }

    override fun onArticleClick(item: Article) {
        openDetailsActivity(item)
    }

    private fun loadingDelay() {
        Handler().postDelayed({
            binding.apiResponseProgressBar.visibility = GONE
            binding.recyclerViewArticle.visibility = VISIBLE
            if (initialToastMessage != "")
                Toast.makeText(
                    this,
                    initialToastMessage,
                    Toast.LENGTH_SHORT
                ).show()
        }, 1000)
    }

}