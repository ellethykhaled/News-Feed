package com.example.newsfeed.ui.details.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import com.example.newsfeed.R
import com.example.newsfeed.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    lateinit var backButton : ImageView

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        binding.backButton.setOnClickListener { finish() }
    }

}