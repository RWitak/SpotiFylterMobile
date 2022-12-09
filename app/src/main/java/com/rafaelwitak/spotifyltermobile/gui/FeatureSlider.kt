package com.rafaelwitak.spotifyltermobile.gui

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.slider.RangeSlider
import com.rafaelwitak.spotifyltermobile.model.AudioFeature
import com.rafaelwitak.spotifyltermobile.model.AudioFeature.*
import com.rafaelwitak.spotifyltermobile.model.QuantizedAudioFeature
import kotlin.math.roundToInt

/**
Each [AudioFeature] gets a separate implementation
Despite (currently used) [AudioFeature]s being measured
as rationals from 0 to 1, [FeatureSlider] uses values from 0 to 100
internally, as the underlying [RangeSlider] prefers integers (though in Float
format) and warns against using true Floats.
 */
sealed class FeatureSlider : RangeSlider {
    abstract val audioFeature: AudioFeature

    override fun getValues(): MutableList<Float> =
        super.getValues().map { it / 100 }.toMutableList()

    init {
        stepSize = 1F
        this.setLabelFormatter { "${it.roundToInt()}%" }

        //    Setting valueFrom and valueTo here via
        //    QuantizedAudioFeature.of(audioFeature).min/.max
        //    crashes at runtime (audioFeature is null),
        //    moved to implementing Classes.
    }

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

    init {
        valueFrom = QuantizedAudioFeature.of(audioFeature).min * 100
        valueTo = QuantizedAudioFeature.of(audioFeature).max * 100
    }

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

    init {
        valueFrom = QuantizedAudioFeature.of(audioFeature).min * 100
        valueTo = QuantizedAudioFeature.of(audioFeature).max * 100
    }

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

    init {
        valueFrom = QuantizedAudioFeature.of(audioFeature).min * 100
        valueTo = QuantizedAudioFeature.of(audioFeature).max * 100
    }

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

    init {
        valueFrom = QuantizedAudioFeature.of(audioFeature).min * 100
        valueTo = QuantizedAudioFeature.of(audioFeature).max * 100
    }

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

    init {
        valueFrom = QuantizedAudioFeature.of(audioFeature).min * 100
        valueTo = QuantizedAudioFeature.of(audioFeature).max * 100
    }

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

    init {
        valueFrom = QuantizedAudioFeature.of(audioFeature).min * 100
        valueTo = QuantizedAudioFeature.of(audioFeature).max * 100
    }

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

    init {
        valueFrom = QuantizedAudioFeature.of(audioFeature).min * 100
        valueTo = QuantizedAudioFeature.of(audioFeature).max * 100
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)
}

