package com.example.newsfeed.ui.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.newsfeed.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        setContentView(R.layout.layout_splash)

        splashDelay()
    }

    private fun splashDelay() {
        val intent = Intent(this, HomeActivity::class.java)

        Handler().postDelayed({
            startActivity(intent)
            finish()
        }, 2000)

    }
}