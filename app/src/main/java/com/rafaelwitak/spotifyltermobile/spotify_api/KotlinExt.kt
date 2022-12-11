package com.rafaelwitak.spotifyltermobile.spotify_api

import android.content.Context
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.endpoints.client.ClientPlayerApi
import com.adamratzman.spotify.models.AudioFeatures
import com.rafaelwitak.spotifyltermobile.model.AudioFeature
import com.rafaelwitak.spotifyltermobile.model.AudioFeature.*
import com.rafaelwitak.spotifyltermobile.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun AudioFeatures.get(featureName: AudioFeature): Float =
    when (featureName) {
        ACOUSTICNESS -> acousticness
        DANCEABILITY -> danceability
        ENERGY -> energy
        INSTRUMENTALNESS -> instrumentalness
        LIVENESS -> liveness
        SPEECHINESS -> speechiness
        VALENCE -> valence
    }

suspend fun <T> SpotifyClientApi.tryToRun(
    context: Context,
    block: ApiCall<T>
): T? =
    withContext(Dispatchers.IO) {
        try {
            block(this@tryToRun)
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                context.toast(e.message.toString(), short = false)
            }
            null
        }
    }

typealias ApiCall<T> = suspend SpotifyClientApi.() -> T
typealias PlayerCall<T> = suspend ClientPlayerApi.() -> T