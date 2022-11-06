package com.rafaelwitak.spotifyltermobile.gui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rafaelwitak.spotifyltermobile.Model
import com.rafaelwitak.spotifyltermobile.SpotiFylterApplication


abstract class BaseActivity : AppCompatActivity() {
    lateinit var model: Model

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        model = Model
    }
}