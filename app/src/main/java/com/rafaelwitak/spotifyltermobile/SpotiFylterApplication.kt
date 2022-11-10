package com.rafaelwitak.spotifyltermobile

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate

class SpotiFylterApplication : Application() {
    init {
        instance = this
    }
    
    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    companion object {
        private lateinit var instance: SpotiFylterApplication

        // TODO: Check if leak-proof
        val context: Context
            get() = instance.applicationContext
    }
}