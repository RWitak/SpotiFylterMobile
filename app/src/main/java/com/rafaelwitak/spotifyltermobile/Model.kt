package com.rafaelwitak.spotifyltermobile

import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
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

}
