package com.rafaelwitak.spotifyltermobile.gui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelwitak.spotifyltermobile.model.AudioFeature
import com.rafaelwitak.spotifyltermobile.model.AudioFeatureSetting
import com.rafaelwitak.spotifyltermobile.model.Model
import com.rafaelwitak.spotifyltermobile.spotify_api.ApiCall
import com.rafaelwitak.spotifyltermobile.spotify_api.PlayerCall
import com.rafaelwitak.spotifyltermobile.spotify_api.allowedBy
import com.rafaelwitak.spotifyltermobile.spotify_api.tryToRun
import com.rafaelwitak.spotifyltermobile.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FylterViewModel(application: Application) :
    AndroidViewModel(application) {
    private val app = application

    val metadataBroadcastReceiver =
        Model.getMetadataBroadcastReceiver { metadataChangedData ->
            Log.i(
                "com.adamratzman.spotify",
                "Spotify Metadata changed: $metadataChangedData"
            )
            viewModelScope.launch {
                skipIfNotMatching {
                    app.toast("Skipped. (New track doesn't match bounds.)")
                }
            }
        }

    private suspend fun <T> tryWithApi(block: ApiCall<T>): T? =
        Model.api?.tryToRun(app, block)

    private suspend fun <T> tryWithPlayer(block: PlayerCall<T>): T? =
        tryWithApi { block(player) }

    private suspend fun notifyBoundsChanged(featureSetting: AudioFeatureSetting) {
        logFeatureBounds(featureSetting)
        skipIfNotMatching {
            app.toast("Skipped. (New bounds don't match track.)")
        }
    }

    private suspend fun skipIfNotMatching(
        onSkipped: (wasSkipped: Boolean) -> Unit = {}
    ) {
        // TODO: Try using only broadcast (or both) for playback state
        if (!isPlaying()) {
            app.toast("No playback detected.")
            onSkipped(false)
            return
        }

        getAudioFeatures(getCurrentTrackId())?.let { features ->
            // TODO: Refactor as FeatureSettings extension
            if (features.allowedBy(Model.featureSettings)) {
                onSkipped(false)
            } else {
                tryWithPlayer {
                    skipForward().also { postRequest ->
                        Log.i("com.adamratzman.spotify", postRequest)
                    }
                    // FIXME: Check if really skipped before calling
                    onSkipped(true)
                }
            }
        }
    }

    private suspend fun isPlaying() =
        tryWithPlayer { getCurrentContext()?.isPlaying } != true

    private suspend fun getAudioFeatures(trackId: String?) =
        withContext(Dispatchers.IO) {
            trackId?.let {
                tryWithApi {
                    tracks.getAudioFeatures(trackId)
                }
            }
        }

    private suspend fun getCurrentTrackId() =
        withContext(Dispatchers.IO) {
            tryWithPlayer {
                getCurrentlyPlaying()?.item?.asTrack?.id
            }
        }

    fun editBounds(audioFeature: AudioFeature, lower: Float, upper: Float) {
        val featureSetting =
            Model.featureSettings.getBoundsFor(audioFeature).apply {
                lowerBound = lower
                upperBound = upper
            }
        viewModelScope.launch { notifyBoundsChanged(featureSetting) }
    }

    private fun logFeatureBounds(featureSetting: AudioFeatureSetting) =
        with(featureSetting) {
            val feature = quantizedFeature.feature.toString()
            Log.i(
                "Bounds",
                "$feature: $lowerBound-$upperBound"
            )
        }
}