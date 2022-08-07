package com.example.newsfeed.utilis

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.newsfeed.R
import com.squareup.picasso.Picasso

@BindingAdapter(value = ["imageUrl"])
fun loadImage(imageView: ImageView, url: String?) {
    if (url != null && url.isEmpty()) {
        imageView.setImageResource(R.drawable.article_image)
        return
    }

    Picasso.get()
        .load(url)
        .placeholder(R.drawable.article_image)
        .error(R.drawable.article_image)
        .into(imageView)
}