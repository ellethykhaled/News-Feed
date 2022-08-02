package com.example.newsfeed.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.newsfeed.R
import com.example.newsfeed.data.model.Article


class ArticleAdapter(private val context: Context, private val arrayList: ArrayList<Article>) : BaseAdapter() {
    private lateinit var articleTitleTextView: TextView
    private lateinit var articleAuthorTextView: TextView
    private lateinit var articleDateTextView: TextView

    override fun getCount(): Int {
        return arrayList.size
    }
    override fun getItem(position: Int): Any {
        return position
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convView: View?, parent: ViewGroup): View? {
        var convertView = convView
        convertView = LayoutInflater.from(context).inflate(R.layout.item_article, parent, false)
        articleTitleTextView = convertView.findViewById(R.id.articleTitle)
        articleAuthorTextView = convertView.findViewById(R.id.articleAuthor)
        articleDateTextView = convertView.findViewById(R.id.articleDate)

        articleTitleTextView.text = arrayList[position].title
        articleAuthorTextView.text = arrayList[position].author
        articleDateTextView.text = arrayList[position].date
        return convertView
    }
}
