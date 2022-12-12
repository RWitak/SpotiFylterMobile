package com.rafaelwitak.spotifyltermobile.model

import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.auth.SpotifyDefaultCredentialStore
import com.adamratzman.spotify.notifications.SpotifyMetadataChangedData
import com.adamratzman.spotify.notifications.SpotifyPlaybackStateChangedData
import com.rafaelwitak.spotifyltermobile.BuildConfig
import com.rafaelwitak.spotifyltermobile.SpotiFylterApplication
import com.rafaelwitak.spotifyltermobile.spotify_api.MetadataBroadcastReceiver
import com.rafaelwitak.spotifyltermobile.spotify_api.PlaybackStateBroadcastReceiver

val REQUIRED_SCOPES = mutableListOf(
    SpotifyScope.APP_REMOTE_CONTROL,
    SpotifyScope.STREAMING,
    SpotifyScope.USER_READ_CURRENTLY_PLAYING,
    SpotifyScope.USER_MODIFY_PLAYBACK_STATE,
    SpotifyScope.USER_READ_PLAYBACK_STATE
)

object Model {
    val featureSettings = AudioFeatureSetting.Collection()

    val credentialStore by lazy {
        SpotifyDefaultCredentialStore(
            clientId = BuildConfig.CLIENT_ID,
            redirectUri = BuildConfig.REDIRECT_URI,
            applicationContext = SpotiFylterApplication.context
        )
    }

    val api: SpotifyClientApi?
        get() = credentialStore.getSpotifyClientPkceApi {
            requiredScopes = REQUIRED_SCOPES
        }

    fun getMetadataBroadcastReceiver(
        onChanged: (SpotifyMetadataChangedData) -> Unit = {}
    ) = MetadataBroadcastReceiver(onChanged)

    fun getPlaybackStateBroadcastReceiver(
        onChanged: (SpotifyPlaybackStateChangedData) -> Unit = {}
    ) = PlaybackStateBroadcastReceiver(onChanged)
}
