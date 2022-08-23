package com.example.newsfeed.ui.home.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.ItemArticleCardViewBinding
import com.example.newsfeed.utilis.formatDate

class ArticleRecyclerViewAdapter(private var articles: List<Article>, private val callback: Callback) :
    RecyclerView.Adapter<ArticleRecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: ItemArticleCardViewBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(position: Int) {
            val itemViewModel = articles[position]
            itemBinding.article = itemViewModel

            itemBinding.root.setOnClickListener {
                callback.onArticleClick(itemViewModel)
            }
            itemBinding.executePendingBindings()
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    fun setArticles(articles: List<Article>) {
        formatDate(articles)
        this.articles = articles
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemArticleCardViewBinding.inflate(inflater, parent, false)

        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ArticleRecyclerViewAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = articles.size

    interface Callback {
        fun onArticleClick(item: Article)
    }
}