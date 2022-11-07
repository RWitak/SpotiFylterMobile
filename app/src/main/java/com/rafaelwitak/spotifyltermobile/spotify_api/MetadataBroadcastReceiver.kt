package com.rafaelwitak.spotifyltermobile.spotify_api

import com.adamratzman.spotify.notifications.AbstractSpotifyBroadcastReceiver
import com.adamratzman.spotify.notifications.SpotifyMetadataChangedData

class MetadataBroadcastReceiver(val onChanged: (SpotifyMetadataChangedData) -> Unit = {}) :
    AbstractSpotifyBroadcastReceiver() {
    override fun onMetadataChanged(data: SpotifyMetadataChangedData) {
        super.onMetadataChanged(data)
        onChanged(data)
    }
}