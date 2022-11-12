package com.rafaelwitak.spotifyltermobile.gui

import android.content.Context
import com.google.android.material.slider.RangeSlider
import com.rafaelwitak.spotifyltermobile.model.AudioFeature

sealed class FeatureSlider(context: Context) : RangeSlider(context) {
    abstract val audioFeature: AudioFeature
}

class AcousticnessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = AudioFeature.ACOUSTICNESS
}

class DanceabilitySlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = AudioFeature.DANCEABILITY
}

class EnergySlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = AudioFeature.ENERGY
}

class InstrumentalnessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = AudioFeature.INSTRUMENTALNESS
}

class LivenessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = AudioFeature.LIVENESS
}

class SpeechinessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = AudioFeature.SPEECHINESS
}

class ValenceSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = AudioFeature.VALENCE
}

