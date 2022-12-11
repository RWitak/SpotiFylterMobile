package com.rafaelwitak.spotifyltermobile.model

import com.adamratzman.spotify.models.AudioFeatures
import com.rafaelwitak.spotifyltermobile.model.AudioFeature.*
import com.rafaelwitak.spotifyltermobile.spotify_api.get

class AudioFeatureSetting(private val quantizedFeature: QuantizedAudioFeature) {
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
                "Out of bounds! " +
                        "Maximum: ${quantizedFeature.max}, Actual: $upperBound"
            }
            field = value
        }

    override fun toString(): String {
        val feature = quantizedFeature.feature.toString()
        return "$feature: $lowerBound-$upperBound"
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

        override fun toString(): String {
            return "$acousticness, " +
                    "$danceability, " +
                    "$energy, " +
                    "$instrumentalness, " +
                    "$liveness, " +
                    "$speechiness, " +
                    "$valence"
        }

        private fun settingOf(audioFeature: AudioFeature): AudioFeatureSetting =
            AudioFeatureSetting(QuantizedAudioFeature.of(audioFeature))

        fun allows(trackFeatures: AudioFeatures) =
            AudioFeature.values().all { audioFeature ->
                val feature = trackFeatures.get(audioFeature)
                val bounds = getBoundsFor(audioFeature)
                feature in bounds
            }
    }
}