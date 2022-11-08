package com.rafaelwitak.spotifyltermobile.model

enum class QuantizedAudioFeature(
    val feature: AudioFeature, val min: Float, val max: Float
) {
    ACOUSTICNESS(AudioFeature.ACOUSTICNESS, 0.0f, 1.0f),
    DANCEABILITY(AudioFeature.DANCEABILITY, 0.0f, 1.0f),
    ENERGY(AudioFeature.ENERGY, 0.0f, 1.0f),
    INSTRUMENTALNESS(AudioFeature.INSTRUMENTALNESS, 0.0f, 1.0f),
    LIVENESS(AudioFeature.LIVENESS, 0.0f, 1.0f),
    SPEECHINESS(AudioFeature.SPEECHINESS, 0.0f, 1.0f),
    VALENCE(AudioFeature.VALENCE, 0.0f, 1.0f);

    companion object {
        fun of(feature: AudioFeature) =
            when (feature) {
                AudioFeature.ACOUSTICNESS -> ACOUSTICNESS
                AudioFeature.DANCEABILITY -> DANCEABILITY
                AudioFeature.ENERGY -> ENERGY
                AudioFeature.INSTRUMENTALNESS -> INSTRUMENTALNESS
                AudioFeature.LIVENESS -> LIVENESS
                AudioFeature.SPEECHINESS -> SPEECHINESS
                AudioFeature.VALENCE -> VALENCE
            }
    }
}