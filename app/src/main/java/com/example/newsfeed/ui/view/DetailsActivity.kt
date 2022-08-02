package com.example.newsfeed.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.newsfeed.R

class DetailsActivity : AppCompatActivity() {
    lateinit var backButton : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setContentView(R.layout.layout_details)

        backButton = findViewById(R.id.backButton)

        backButton.setOnClickListener { finish() }
    }

}