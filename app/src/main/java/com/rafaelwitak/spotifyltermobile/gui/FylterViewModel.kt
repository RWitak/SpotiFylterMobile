package com.rafaelwitak.spotifyltermobile.gui

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.rafaelwitak.spotifyltermobile.SpotiFylterApplication
import com.rafaelwitak.spotifyltermobile.model.AudioFeatureSetting
import com.rafaelwitak.spotifyltermobile.model.Model
import com.rafaelwitak.spotifyltermobile.spotify_api.allowedBy
import com.rafaelwitak.spotifyltermobile.util.LoginState
import com.rafaelwitak.spotifyltermobile.util.NetConnectCallbackImpl
import com.rafaelwitak.spotifyltermobile.util.TAG
import com.rafaelwitak.spotifyltermobile.util.toast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FylterViewModel(application: Application) :
    AndroidViewModel(application) {
    private val _loginState: MutableStateFlow<LoginState> =
        MutableStateFlow(LoginState.Checking(hasInternet = false))
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()

    private fun MutableStateFlow<LoginState>.transform(stateTransform: (LoginState) -> LoginState) {
        viewModelScope.launch { getAndUpdate(stateTransform) }
    }

    private val api by lazy {
        Model.credentialStore.getSpotifyClientPkceApi {
            // TODO: Disable in production
            enableLogger = true
            enableDebugMode = true
        }?.also {
            application.toast("Api created successfully!")
            _loginState.transform { LoginState.Connected(it.hasInternet) }
        }
    }

    private val player by lazy { api?.player }

    val featureSettings = Model.featureSettings
    val metadataBroadcastReceiver =
        Model.getMetadataBroadcastReceiver { metadataChangedData ->
            Log.i(
                "com.adamratzman.spotify",
                "Spotify Metadata changed: $metadataChangedData"
            )
            viewModelScope.launch { skipIfNotMatching() }
        }


    init {
        observeNetworkConnection(application)
    }

    private fun observeNetworkConnection(application: Application) {
        NetConnectCallbackImpl(
            application = application,
            onConnected = {
                _loginState.transform { state ->
                    state.copyWith(hasInternet = true)
                }
            },
            onDisconnected = {
                _loginState.transform { state ->
                    state.copyWith(hasInternet = false)
                }
            }
        ).activate()
    }


    private suspend fun notifyBoundsChanged(featureSetting: AudioFeatureSetting) {
        logFeatureBounds(featureSetting)
        skipIfNotMatching()
    }

    private suspend fun skipIfNotMatching(onSkipped: (Boolean) -> Unit = {}) =
        withContext(Dispatchers.IO) {
            getAudioFeatures(getCurrentTrackId())?.let { features ->
                Log.i(TAG, features.toString())
                if (features.allowedBy(featureSettings)) {
                    return@let
                }
                player?.skipForward().also {
                    getApplication<SpotiFylterApplication>()
                        .toast("Track skipped")
                    Log.i("com.adamratzman.spotify", it.toString())
                    onSkipped(true)
                } ?: Log.e(TAG, "No player")
            }
            onSkipped(false)
        }

    private suspend fun getAudioFeatures(id: String?) =
        withContext(Dispatchers.IO) {
            id?.let {
                api?.tracks?.getAudioFeatures(id)
            }
        }

    private suspend fun getCurrentTrackId() =
        withContext(Dispatchers.IO) {
            player?.getCurrentlyPlaying()
                ?.item?.asTrack?.id
        }

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

    private fun logFeatureBounds(featureSetting: AudioFeatureSetting) =
        with(featureSetting) {
            val feature = quantizedFeature.feature.toString()
            Log.i(
                "$TAG: Bounds",
                "$feature: $lowerBound-$upperBound"
            )
        }
}
