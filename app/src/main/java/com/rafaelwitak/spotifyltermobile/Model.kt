package com.rafaelwitak.spotifyltermobile

import android.app.Application
import androidx.lifecycle.LiveData
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.notifications.*
import com.rafaelwitak.spotifyltermobile.util.AudioFeatureSetting

object Model {
    private val spotifyBroadcastReceiver = SpotifyBroadcastReceiver
    val metadata = LiveMetaData()
    val featureSettings = AudioFeatureSetting.Collection()
    private val broadcasts: MutableList<SpotifyBroadcastEventData> = mutableListOf()

    init {
        Application().registerSpotifyBroadcastReceiver(
            spotifyBroadcastReceiver,
            *SpotifyBroadcastType.values()
        )
    }

    val credentialStore by lazy {
        SpotifyDefaultCredentialStore(
            clientId = BuildConfig.CLIENT_ID,
            redirectUri = BuildConfig.REDIRECT_URI,
            applicationContext = SpotiFylterApplication.context
        )
    }

    private object SpotifyBroadcastReceiver :
        AbstractSpotifyBroadcastReceiver() {
        override fun onMetadataChanged(data: SpotifyMetadataChangedData) {
            super.onMetadataChanged(data)
                broadcasts.add(data)
        }
    }
}

class LiveMetaData : LiveData<SpotifyMetadataChangedData>()
