package com.example.newsfeed.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.ItemArticleCardViewBinding
import com.squareup.picasso.Picasso

class ArticleAdapter(private var articles: List<Article>, private val callback: Callback) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

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

    fun setArticles(articles: List<Article>) {
        this.articles = articles
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemArticleCardViewBinding.inflate(inflater, parent, false)

        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount() = articles.size

    interface Callback {
        fun onArticleClick(item: Article)
    }
}