package com.rafaelwitak.spotifyltermobile.gui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.adamratzman.spotify.notifications.SpotifyBroadcastType
import com.adamratzman.spotify.notifications.registerSpotifyBroadcastReceiver
import com.google.android.material.slider.RangeSlider
import com.rafaelwitak.spotifyltermobile.databinding.ActivityMainBinding

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

        binding = ActivityMainBinding.inflate(layoutInflater)
            .apply {
                setContentView(root)
                settings = vm.featureSettings
            }

        setUpSliders()
    }

    private fun setUpSliders() {
        class OnSliderTouchListener : RangeSlider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) =
                vm.sliderTouchStart(slider as FeatureSlider)

            override fun onStopTrackingTouch(slider: RangeSlider) =
                vm.sliderTouchStop(slider as FeatureSlider)
        }

        val sliders = with(binding) {
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

        sliders.forEach { featureSlider ->
            featureSlider.values =
                mutableListOf(featureSlider.valueFrom, featureSlider.valueTo)
            featureSlider.addOnSliderTouchListener(
                OnSliderTouchListener()
            )
        }
    }
}