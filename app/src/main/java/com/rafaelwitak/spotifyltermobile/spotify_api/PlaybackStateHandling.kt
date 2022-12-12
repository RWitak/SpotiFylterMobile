package com.rafaelwitak.spotifyltermobile.spotify_api

import com.adamratzman.spotify.endpoints.client.ClientPlayerApi
import com.adamratzman.spotify.notifications.SpotifyPlaybackStateChangedData

private data class TimestampedPlayingState(
    val isPlaying: Boolean,
    val timestamp: Long
)

private suspend fun ClientPlayerApi.getTimestampedPlayingState() =
    getCurrentContext()?.run {
        TimestampedPlayingState(isPlaying, timestamp)
    }

private fun SpotifyPlaybackStateChangedData.getTimestampedPlayingState() =
    TimestampedPlayingState(playing, timeSentInMs)

/** Take most recent PlayingState (if any) from Metadata and Player data. */
suspend fun getMostRecentPlayingState(
    playerApi: ClientPlayerApi?,
    playbackStateChangedData: SpotifyPlaybackStateChangedData?
): Boolean? {
    val playerInfo = playerApi?.getTimestampedPlayingState()
    val spotifyTimestampedPlayingState = playbackStateChangedData
        ?.getTimestampedPlayingState()

    return listOfNotNull(spotifyTimestampedPlayingState, playerInfo)
        .maxByOrNull(TimestampedPlayingState::timestamp)
        ?.isPlaying
}