package com.rafaelwitak.spotifyltermobile.util

import java.util.*

enum class AudioFeature {
    ACOUSTICNESS,
    DANCEABILITY,
    ENERGY,
    INSTRUMENTALNESS,
    LIVENESS,
    SPEECHINESS,
    VALENCE;

    override fun toString(): String {
        val name = super.toString()
        return name[0].toString() + name.substring(1)
            .lowercase(Locale.getDefault())
    }
}