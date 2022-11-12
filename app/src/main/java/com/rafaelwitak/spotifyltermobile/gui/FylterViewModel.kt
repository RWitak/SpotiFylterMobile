package com.rafaelwitak.spotifyltermobile.gui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelwitak.spotifyltermobile.R
import com.rafaelwitak.spotifyltermobile.model.AudioFeatureSetting
import com.rafaelwitak.spotifyltermobile.model.Model
import com.rafaelwitak.spotifyltermobile.spotify_api.allowedBy
import com.rafaelwitak.spotifyltermobile.util.toast
import kotlinx.coroutines.launch

class FylterViewModel(application: Application) :
    AndroidViewModel(application) {
    private val api by lazy {
        Model.credentialStore.getSpotifyClientPkceApi { }
            .also { app.toast(application.getString(R.string.api_connected)) }
    }
    private val player by lazy { api?.player }
    private val app: Application
        get() = getApplication()

    val featureSettings = Model.featureSettings
    val metadataBroadcastReceiver =
        Model.getMetadataBroadcastReceiver { metadataChangedData ->
            Log.i(
                "com.adamratzman.spotify",
                "Spotify Metadata changed: $metadataChangedData"
            )
            viewModelScope.launch { skipIfNotMatching() }
        }

    //    private fun hasInternet(): Boolean {
//        val connectivityManager: ConnectivityManager =
//            app.getSystemService(
//                ConnectivityManager::class.java
//            )
//        val activeNetwork: Network? = connectivityManager.activeNetwork
//        val capabilities: NetworkCapabilities? =
//            connectivityManager.getNetworkCapabilities(activeNetwork)
//        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
//            ?: false
//    }

    private suspend fun notifyBoundsChanged(featureSetting: AudioFeatureSetting) {
        Log.i(
            "Bounds",
            featureSetting.quantizedFeature.feature.toString() + ": "
                    + featureSetting.lowerBound + "-"
                    + featureSetting.upperBound
        )
        skipIfNotMatching()
    }

    private suspend fun skipIfNotMatching(onSkipped: (Boolean) -> Unit = {}) {
        getAudioFeatures(getCurrentTrackId())
            ?.let { features ->
                if (!features.allowedBy(featureSettings)) {
                    player?.skipForward().also {
                        onSkipped(true)
                        app.toast("Track skipped")
                        Log.i("com.adamratzman.spotify", it.toString())
                    }
                }
            }
        onSkipped(false)
    }

    private suspend fun getAudioFeatures(id: String?) =
        id?.let {
            api?.tracks?.getAudioFeatures(id)
        }

    private suspend fun getCurrentTrackId() = player?.getCurrentlyPlaying()
        ?.item?.asTrack?.id

    fun sliderTouchStart(@Suppress("UNUSED_PARAMETER") slider: FeatureSlider) {
        viewModelScope.launch { player?.pause() }
    }

    fun sliderTouchStop(slider: FeatureSlider) {
        val featureSetting = with(slider) {
            featureSettings.getBoundsFor(audioFeature).apply {
                lowerBound = values.min()
                upperBound = values.max()
            }
        }
        viewModelScope.launch { notifyBoundsChanged(featureSetting) }
    }
}
