package com.example.newsfeed.common

import androidx.appcompat.app.AppCompatActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

open class BasicActivity: AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
}