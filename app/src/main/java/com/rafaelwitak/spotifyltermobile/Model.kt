package com.rafaelwitak.spotifyltermobile

import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.notifications.SpotifyMetadataChangedData
import com.rafaelwitak.spotifyltermobile.spotify_api.MetadataBroadcastReceiver
import com.rafaelwitak.spotifyltermobile.util.AudioFeatureSetting

object Model {
    val featureSettings = AudioFeatureSetting.Collection()

    val credentialStore by lazy {
        SpotifyDefaultCredentialStore(
            clientId = BuildConfig.CLIENT_ID,
            redirectUri = BuildConfig.REDIRECT_URI,
            applicationContext = SpotiFylterApplication.context
        )
    }

    fun getMetadataBroadcastReceiver(onChanged: (SpotifyMetadataChangedData) -> Unit = {}) =
        MetadataBroadcastReceiver(onChanged)
}
