package com.example.newsfeed.ui.home.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
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
        displayFirstConnectionToast()
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
                val sendFailureErrorRemote =
                    it.dataSource == DataWrapper.REMOTE && viewModel.isRemote
                val sendFailureErrorLocal =
                    it.dataSource == DataWrapper.LOCAL && !viewModel.isLocal
                if (sendFailureErrorRemote)
                    viewModel.isRemote = false
                if (sendFailureErrorLocal)
                    viewModel.isLocal = false
                if (sendFailureErrorRemote || sendFailureErrorLocal)
                    displayToast("Error Loading Data")
            }
            is DataWrapper.Success -> {
                binding.refresher.isRefreshing = false
                if (viewModel.firstConnection) {
                    bindNewData(it)
                    return
                }
                if (it.dataSource == DataWrapper.REMOTE) {
                    viewModel.isRemote = true
                    displayToast(HomeActivityViewModel.ONLINE_MESSAGE)
                    bindNewData(it)
                } else if (it.dataSource == DataWrapper.LOCAL && !viewModel.isRemote) {
                    viewModel.isLocal = true
                    displayToast(HomeActivityViewModel.OFFLINE_MESSAGE)
                    bindNewData(it)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindNewData(it: DataWrapper<List<Article>>) {
        it.data?.let {
            bindArticlesData(it)
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

    private fun openDetailsActivity(articlePosition: Int) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(DetailsActivity.ARTICLE_POSITION, articlePosition)
        startActivity(intent)
    }

    override fun onArticleClick(articlePosition: Int) {
        openDetailsActivity(articlePosition)
    }

    private fun displayFirstConnectionToast() {
        Handler().postDelayed({
            viewModel.firstConnection = false
            if (viewModel.isRemote)
                displayToast(HomeActivityViewModel.ONLINE_MESSAGE)
            else if (viewModel.isLocal)
                displayToast(HomeActivityViewModel.ONLINE_MESSAGE)
        }, 750)
    }

    private fun displayToast(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}