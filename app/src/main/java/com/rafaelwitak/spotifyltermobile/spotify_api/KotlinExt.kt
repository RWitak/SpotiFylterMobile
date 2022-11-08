package com.rafaelwitak.spotifyltermobile.spotify_api

import com.adamratzman.spotify.models.AudioFeatures
import com.rafaelwitak.spotifyltermobile.model.AudioFeature
import com.rafaelwitak.spotifyltermobile.model.AudioFeature.*
import com.rafaelwitak.spotifyltermobile.model.AudioFeatureSetting

fun AudioFeatures.get(featureName: AudioFeature): Float =
    when (featureName) {
        ACOUSTICNESS -> acousticness
        DANCEABILITY -> danceability
        ENERGY -> energy
        INSTRUMENTALNESS -> instrumentalness
        LIVENESS -> liveness
        SPEECHINESS -> speechiness
        VALENCE -> valence
    }

fun AudioFeatures.allowedBy(featureSettings: AudioFeatureSetting.Collection) =
    AudioFeature.values().all { featureName ->
        val bounds = featureSettings.getBoundsFor(featureName)
        get(featureName) in bounds
    }