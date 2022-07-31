package com.example.newsfeed

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView


class ArticleAdapter(private val context: Context, private val arrayList: ArrayList<Article>) : BaseAdapter() {
    private lateinit var articleTitle: TextView
    private lateinit var articleAuthor: TextView
    private lateinit var articleDate: TextView

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
        convertView = LayoutInflater.from(context).inflate(R.layout.article, parent, false)
        articleTitle = convertView.findViewById(R.id.articleTitle)
        articleAuthor = convertView.findViewById(R.id.articleAuthor)
        articleDate = convertView.findViewById(R.id.articleDate)

        articleTitle.text = arrayList[position].title
        articleAuthor.text = arrayList[position].author
        articleDate.text = arrayList[position].date
        return convertView
    }
}
