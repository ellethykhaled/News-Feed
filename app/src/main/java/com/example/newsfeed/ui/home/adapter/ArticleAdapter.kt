package com.example.newsfeed.ui.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.newsfeed.data.model.Article
import com.example.newsfeed.databinding.ItemArticleCardViewBinding
import com.example.newsfeed.ui.details.view.DetailsActivity

class ArticleAdapter(private val articles: List<Article>) : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    inner class ViewHolder(private val itemBinding: ItemArticleCardViewBinding) : RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(position: Int) {
            itemBinding.article = articles[position]

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val listItemBinding = ItemArticleCardViewBinding.inflate(inflater, parent, false)

        return ViewHolder(listItemBinding)
    }

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return articles.size
    }
}
