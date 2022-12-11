package com.rafaelwitak.spotifyltermobile.spotify_api

import com.adamratzman.spotify.endpoints.client.ClientPlayerApi
import com.adamratzman.spotify.notifications.SpotifyMetadataChangedData

data class TimestampedTrackId(val id: String, val timestamp: Long)

suspend fun ClientPlayerApi.getTimestampedTrackId(): TimestampedTrackId? =
    getCurrentlyPlaying()?.run {
        item?.asTrack?.id?.let { id ->
            TimestampedTrackId(id, timestamp)
        }
    }

fun SpotifyMetadataChangedData.getTimestampedTrackId() =
    TimestampedTrackId(playableUri.id, timeSentInMs)

/** Take most recent id (if any) from Metadata and Player data. */
suspend fun getMostRecentTrackId(
    playerApi: ClientPlayerApi?,
    metadata: SpotifyMetadataChangedData?
): String? {
    val playerInfo = playerApi?.getTimestampedTrackId()
    val metadataTimestampedTrackId = metadata?.getTimestampedTrackId()

    return listOfNotNull(metadataTimestampedTrackId, playerInfo)
        .maxByOrNull(TimestampedTrackId::timestamp)
        ?.id
}