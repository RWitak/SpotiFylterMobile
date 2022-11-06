package com.rafaelwitak.spotifyltermobile

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class SpotiFylterApplication : Application() {
    init {
        instance = this
    }
    
    lateinit var model: Model

    override fun onCreate() {
        super.onCreate()
        model = Model
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )
    }

    companion object {
        private lateinit var instance: SpotiFylterApplication
        val context: Context
            get() = instance.applicationContext
    }
}