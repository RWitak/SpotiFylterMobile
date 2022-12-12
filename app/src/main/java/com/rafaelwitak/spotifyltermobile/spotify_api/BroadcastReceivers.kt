package com.rafaelwitak.spotifyltermobile.spotify_api

import com.adamratzman.spotify.notifications.AbstractSpotifyBroadcastReceiver
import com.adamratzman.spotify.notifications.SpotifyMetadataChangedData
import com.adamratzman.spotify.notifications.SpotifyPlaybackStateChangedData

class MetadataBroadcastReceiver(val onChanged: (SpotifyMetadataChangedData) -> Unit = {}) :
    AbstractSpotifyBroadcastReceiver() {
    override fun onMetadataChanged(data: SpotifyMetadataChangedData) {
        super.onMetadataChanged(data)
        onChanged(data)
    }
}

class PlaybackStateBroadcastReceiver(
    val onChanged: (SpotifyPlaybackStateChangedData) -> Unit = {}
) : AbstractSpotifyBroadcastReceiver() {
    override fun onPlaybackStateChanged(data: SpotifyPlaybackStateChangedData) {
        super.onPlaybackStateChanged(data)
        onChanged(data)
    }
}