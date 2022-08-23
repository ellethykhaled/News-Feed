package com.example.newsfeed.ui.home.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.example.newsfeed.R
import com.example.newsfeed.common.BasicActivity
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.utilis.DataWrapper
import com.example.newsfeed.databinding.ActivityHomeBinding
import com.example.newsfeed.ui.details.view.DetailsActivity
import com.example.newsfeed.ui.home.HomeViewModelProviderFactory
import com.example.newsfeed.ui.home.view.adapter.ArticleRecyclerViewAdapter
import com.example.newsfeed.ui.home.viewmodel.HomeActivityViewModel
import org.kodein.di.generic.instance

class HomeActivity : BasicActivity(), ArticleRecyclerViewAdapter.Callback {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeActivityViewModel
    private lateinit var articleRecyclerViewAdapter: ArticleRecyclerViewAdapter

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

        binding.recyclerViewArticle.apply {
            adapter = ArticleRecyclerViewAdapter(emptyList(), this@HomeActivity)
            addItemDecoration(HomeListItemDecorator(20))
        }

        binding.refresher.apply {
            setOnRefreshListener {
                viewModel.getArticlesData()
                viewModel.isOnlineBefore = true
            }
            isRefreshing = true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun initViewModel() {
        val viewModelProviderFactory: HomeViewModelProviderFactory by kodein.instance()

        viewModel =
            ViewModelProviders.of(this, viewModelProviderFactory)[HomeActivityViewModel::class.java]

        setArticleAdapter(binding.recyclerViewArticle.adapter as ArticleRecyclerViewAdapter)

        viewModel.liveData.observe(this) {
            onLiveDataChange(it)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onLiveDataChange(it: DataWrapper<List<Article>>) {
        when (it) {
            is DataWrapper.Loading -> {}
            is DataWrapper.Failure -> {
                if (!viewModel.isOnlineBefore && it.dataSource == DataWrapper.LOCAL)
                    Toast.makeText(
                        this,
                        "Error Loading Data",
                        Toast.LENGTH_SHORT
                    ).show()
            }
            is DataWrapper.Success -> {
                binding.refresher.isRefreshing = false
                if (viewModel.isOnlineBefore)
                    return
                it.data?.let {
                    bindArticlesData(it)
                }
                viewModel.changeToastMessage(it.dataSource)
                if (it.dataSource == DataWrapper.REMOTE) {
                    viewModel.isOnlineBefore = true
                    Toast.makeText(
                        this,
                        viewModel.toastMessage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("NotifyDataSetChanged")
    private fun bindArticlesData(articleList: List<Article>) {
        val adapter = binding.recyclerViewArticle.adapter as? ArticleRecyclerViewAdapter
        adapter?.setArticles(articleList)
    }

    @JvmName("setArticleAdapter1")
    fun setArticleAdapter(recyclerViewAdapter: ArticleRecyclerViewAdapter) {
        articleRecyclerViewAdapter = recyclerViewAdapter
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