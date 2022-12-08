package com.rafaelwitak.spotifyltermobile.gui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.slider.RangeSlider
import com.rafaelwitak.spotifyltermobile.model.AudioFeature
import com.rafaelwitak.spotifyltermobile.model.AudioFeature.*

sealed class FeatureSlider : RangeSlider {
    abstract val audioFeature: AudioFeature

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    fun resetRange() {
        values = mutableListOf(valueFrom, valueTo)
    }

    fun addOnTouchListener(
        onStart: (FeatureSlider) -> Unit = {},
        onStop: (FeatureSlider) -> Unit = {}
    ) {
        addOnSliderTouchListener(object : OnTouchListener {
            override fun onStartTrackingTouch(slider: RangeSlider) =
                onStart(slider as FeatureSlider)

            override fun onStopTrackingTouch(slider: RangeSlider) =
                onStop(slider as FeatureSlider)
        })
    }

    interface OnTouchListener : OnSliderTouchListener
}

class AcousticnessSlider : FeatureSlider {
    override val audioFeature = ACOUSTICNESS

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

class DanceabilitySlider : FeatureSlider {
    override val audioFeature = DANCEABILITY

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

class EnergySlider : FeatureSlider {
    override val audioFeature = ENERGY

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

class InstrumentalnessSlider : FeatureSlider {
    override val audioFeature = INSTRUMENTALNESS

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

class LivenessSlider : FeatureSlider {
    override val audioFeature = LIVENESS

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

class SpeechinessSlider : FeatureSlider {
    override val audioFeature = SPEECHINESS

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

class ValenceSlider : FeatureSlider {
    override val audioFeature = VALENCE

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

