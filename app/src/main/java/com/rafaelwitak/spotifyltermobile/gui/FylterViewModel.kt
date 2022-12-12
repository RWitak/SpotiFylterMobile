package com.rafaelwitak.spotifyltermobile.gui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.adamratzman.spotify.notifications.SpotifyMetadataChangedData
import com.adamratzman.spotify.notifications.SpotifyPlaybackStateChangedData
import com.rafaelwitak.spotifyltermobile.R
import com.rafaelwitak.spotifyltermobile.SpotiFylterApplication.Companion.context
import com.rafaelwitak.spotifyltermobile.model.AudioFeature
import com.rafaelwitak.spotifyltermobile.model.AudioFeatureSetting
import com.rafaelwitak.spotifyltermobile.model.Model
import com.rafaelwitak.spotifyltermobile.spotify_api.*
import com.rafaelwitak.spotifyltermobile.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FylterViewModel(application: Application) :
    AndroidViewModel(application) {
    private val app = application

    @Volatile
    private var metadata: SpotifyMetadataChangedData? = null
        set(value) {
            field = value
            Log.i("Metadata", "changed: $value")
        }

    @Volatile
    private var playbackState: SpotifyPlaybackStateChangedData? = null
        set(value) {
            field = value
            Log.i("PlaybackState", "changed: $value")
        }

    val metadataBroadcastReceiver =
        Model.getMetadataBroadcastReceiver {
            metadata = it
            viewModelScope.launch { skipIfNotMatching() }
        }

    val playbackStateBroadcastReceiver =
        Model.getPlaybackStateBroadcastReceiver {
            playbackState = it
        }

    private suspend fun <T> tryWithApi(block: ApiCall<T>): T? =
        Model.api?.tryToRun(app, block)

    private suspend fun <T> tryWithPlayer(block: PlayerCall<T>): T? =
        tryWithApi { block(player) }

    private suspend fun notifyBoundsChanged(featureSetting: AudioFeatureSetting) {
        Log.i("Bounds", featureSetting.toString())
        skipIfNotMatching { skip ->
            val msg = if (skip) {
                val reason =
                    context.getString(R.string.track_mismatch_new_bounds)
                context.getString(
                    R.string.skipped_with_reason,
                    reason
                )
            } else context.getString(R.string.track_match_new_bounds)

            app.toast(msg)
        }
    }

    // FIXME: Quickly runs through many skips, eventually creating a 403 from
    //  Spotify, probably for trying to skip while in a non-skippable state.
    //  This issue seems to be caused by one of the underlying APIs and is
    //  currently under investigation.
    private suspend fun skipIfNotMatching(
        onSkipAttempt: (wasSkipped: Boolean) -> Unit = {}
    ) {
        if (isPlaying() != true) {
            app.toast(context.getString(R.string.no_playback))
            return onSkipAttempt(false)
        }

        val features = getAudioFeatures(
            getMostRecentTrackId(tryWithApi { player }, metadata)
        )
        if (features == null || Model.featureSettings.allows(features))
            return onSkipAttempt(false)

        Log.d(
            "ClientPlayerApi",
            "Track Features: $features, Settings: ${Model.featureSettings}"
        )

        tryWithPlayer {
            skipForward().also { postRequest ->
                Log.i("ClientPlayerApi", postRequest)
            }
            // FIXME: Check if really skipped (by this) before calling:
            onSkipAttempt(true)
        }
    }

    private suspend fun isPlaying() =
        getMostRecentPlayingState(tryWithApi { player }, playbackState)

    private suspend fun getAudioFeatures(trackId: String?) =
        withContext(Dispatchers.IO) {
            trackId?.let {
                tryWithApi { tracks.getAudioFeatures(trackId) }
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
}