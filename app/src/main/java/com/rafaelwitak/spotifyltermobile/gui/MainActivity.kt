package com.rafaelwitak.spotifyltermobile.gui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.adamratzman.spotify.notifications.SpotifyBroadcastType
import com.adamratzman.spotify.notifications.registerSpotifyBroadcastReceiver
import com.google.android.material.slider.RangeSlider
import com.rafaelwitak.spotifyltermobile.databinding.ActivityMainBinding
import com.rafaelwitak.spotifyltermobile.util.LoginState
import com.rafaelwitak.spotifyltermobile.util.toast
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: FylterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        title = "Specify Audio Features"
        registerSpotifyBroadcastReceiver(
            vm.metadataBroadcastReceiver,
            SpotifyBroadcastType.MetadataChanged
        )

        observeLoginState()

        binding = ActivityMainBinding.inflate(layoutInflater)
            .apply {
                setContentView(root)
                settings = vm.featureSettings
            }

        setUpSliders()
    }

    private fun observeLoginState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.loginState.collect(::handleLoginState)
            }
        }

    }

    private fun onReady() {
        // TODO: clear screen and display main interface
    }

    private fun handleLoginState(loginState: LoginState) {
        if (!loginState.hasInternet) {
            toast("No internet connection")
            return
        }
        when (loginState) {
            is LoginState.AuthFailed -> toast("Authentication failed")
            is LoginState.Checking -> toast("Checking...")
            is LoginState.Connected -> onReady().also { toast("Connected") }
            is LoginState.NotInstalled -> toast("Spotify not installed")
        }
    }

    private fun setUpSliders() {
        class OnSliderTouchListener : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) =
                vm.sliderTouchStart(slider as FeatureSlider)

            override fun onStopTrackingTouch(slider: RangeSlider) =
                vm.sliderTouchStop(slider as FeatureSlider)
        }

        featureSliders.forEach { featureSlider ->
            featureSlider.values =
                mutableListOf(
                    // FIXME: Config changes set sliders' max ranges to current bounds
                    featureSlider.valueFrom,
                    featureSlider.valueTo
                )
            featureSlider.addOnSliderTouchListener(
                OnSliderTouchListener()
            )
        }
    }

    private val featureSliders
        get() = with(binding) {
            listOf(
                acousticnessSlider,
                danceabilitySlider,
                energySlider,
                instrumentalnessSlider,
                livenessSlider,
                speechinessSlider,
                valenceSlider,
            )
        }
}