package com.rafaelwitak.spotifyltermobile.model

enum class AudioFeature {
    ACOUSTICNESS,
    DANCEABILITY,
    ENERGY,
    INSTRUMENTALNESS,
    LIVENESS,
    SPEECHINESS,
    VALENCE;

    override fun toString() =
        super.toString().lowercase().replaceFirstChar(Char::uppercase)
}