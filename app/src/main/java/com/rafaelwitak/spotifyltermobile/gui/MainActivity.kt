package com.rafaelwitak.spotifyltermobile.gui

import android.app.Activity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.adamratzman.spotify.auth.pkce.startSpotifyClientPkceLoginActivity
import com.adamratzman.spotify.notifications.SpotifyBroadcastType
import com.adamratzman.spotify.notifications.registerSpotifyBroadcastReceiver
import com.rafaelwitak.spotifyltermobile.databinding.ActivityMainBinding
import com.rafaelwitak.spotifyltermobile.model.Model
import com.rafaelwitak.spotifyltermobile.spotify_api.SpotifyPkceLoginActivityImpl
import com.rafaelwitak.spotifyltermobile.spotify_api.pkceClassBackTo

private fun ActivityMainBinding.getFeatureSliders() =
    listOf(
        acousticnessSlider,
        danceabilitySlider,
        energySlider,
        instrumentalnessSlider,
        livenessSlider,
        speechinessSlider,
        valenceSlider,
    )

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: FylterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Specify Audio Features"

        logInIfNecessary(this::class.java)

        // Listen to changing Spotify Metadata
        registerSpotifyBroadcastReceiver(
            vm.metadataBroadcastReceiver,
            SpotifyBroadcastType.MetadataChanged
        )

        binding = ActivityMainBinding.inflate(layoutInflater)
            .apply { setContentView(root) }

        setUpSliders()
    }

    private fun logInIfNecessary(activityClass: Class<out Activity>) {
        if (Model.credentialStore.spotifyToken == null) {
            pkceClassBackTo = activityClass
            startSpotifyClientPkceLoginActivity(
                SpotifyPkceLoginActivityImpl::class.java
            )
        }
    }

    private fun setUpSliders() {
        fun FeatureSlider.propagateBounds() =
            vm.editBounds(audioFeature, values.min(), values.max())

        binding.getFeatureSliders().forEach { slider ->
            slider.resetRange()
            slider.addOnTouchListener(onStop = FeatureSlider::propagateBounds)
        }
    }
}