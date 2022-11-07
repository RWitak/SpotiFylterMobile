package com.rafaelwitak.spotifyltermobile.util

import com.rafaelwitak.spotifyltermobile.util.AudioFeature.*

class AudioFeatureSetting(val quantizedFeature: QuantizedAudioFeature) {
    var lowerBound: Float = quantizedFeature.min
        set(value) {
            require(lowerBound >= quantizedFeature.min) {
                "Out of bounds! " +
                        "Minimum: ${quantizedFeature.min}, Actual: $lowerBound"
            }
            field = value
        }
    var upperBound: Float = quantizedFeature.max
        set(value) {
            require(upperBound <= quantizedFeature.max) {
                "Out of bounds!" +
                        "Maximum: ${quantizedFeature.max}, Actual: $upperBound"
            }
            field = value
        }

    init {
        lowerBound = quantizedFeature.min
        upperBound = quantizedFeature.max
    }

    operator fun contains(value: Float) = value in lowerBound..upperBound

    class Collection {
        val acousticness = settingOf(ACOUSTICNESS)
        val danceability = settingOf(DANCEABILITY)
        val energy = settingOf(ENERGY)
        val instrumentalness = settingOf(INSTRUMENTALNESS)
        val liveness = settingOf(LIVENESS)
        val speechiness = settingOf(SPEECHINESS)
        val valence = settingOf(VALENCE)

        fun getBoundsFor(feature: AudioFeature) = when (feature) {
            ACOUSTICNESS -> acousticness
            DANCEABILITY -> danceability
            ENERGY -> energy
            INSTRUMENTALNESS -> instrumentalness
            LIVENESS -> liveness
            SPEECHINESS -> speechiness
            VALENCE -> valence
        }

        private fun settingOf(audioFeature: AudioFeature): AudioFeatureSetting =
            AudioFeatureSetting(QuantizedAudioFeature.of(audioFeature))
    }
}