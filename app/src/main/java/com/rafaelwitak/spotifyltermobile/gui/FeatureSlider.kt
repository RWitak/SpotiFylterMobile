package com.rafaelwitak.spotifyltermobile.gui

import android.content.Context
import com.google.android.material.slider.RangeSlider
import com.rafaelwitak.spotifyltermobile.model.AudioFeature
import com.rafaelwitak.spotifyltermobile.model.AudioFeature.*

sealed class FeatureSlider(context: Context) : RangeSlider(context) {
    abstract val audioFeature: AudioFeature
}

class AcousticnessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = ACOUSTICNESS
}

class DanceabilitySlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = DANCEABILITY
}

class EnergySlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = ENERGY
}

class InstrumentalnessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = INSTRUMENTALNESS
}

class LivenessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = LIVENESS
}

class SpeechinessSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = SPEECHINESS
}

class ValenceSlider(context: Context) : FeatureSlider(context) {
    override val audioFeature = VALENCE
}

