package com.example.newsfeed.ui.home.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.data.repository.DataRepo
import com.example.newsfeed.databinding.ActivityHomeBinding
import com.example.newsfeed.ui.details.view.DetailsActivity
import com.example.newsfeed.ui.home.adapter.ArticleAdapter
import com.example.newsfeed.ui.home.viewmodel.HomeActivityViewModel

class HomeActivity : AppCompatActivity(), ArticleAdapter.Callback {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var manager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)


        manager = LinearLayoutManager(this)

        binding.recyclerViewArticle.apply {
            adapter = ArticleAdapter(emptyList(), this@HomeActivity)

            layoutManager = manager
        }

        initViewModel()
    }

    private fun initViewModel() {
        val viewModel: HomeActivityViewModel =
            ViewModelProvider(this).get(HomeActivityViewModel::class.java)

        val dataRepo = DataRepo()
        dataRepo.makeAPICall(viewModel.getLiveDataObserver())

        viewModel.setArticleAdapter(binding.recyclerViewArticle.adapter as ArticleAdapter)

        viewModel.getLiveDataObserver().observe(this, Observer {
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
}