package com.rafaelwitak.spotifyltermobile.spotify_api

import android.app.Activity
import android.content.Intent
import com.adamratzman.spotify.SpotifyClientApi
import com.adamratzman.spotify.auth.pkce.AbstractSpotifyPkceLoginActivity
import com.rafaelwitak.spotifyltermobile.BuildConfig
import com.rafaelwitak.spotifyltermobile.gui.MainActivity
import com.rafaelwitak.spotifyltermobile.model.Model
import com.rafaelwitak.spotifyltermobile.model.REQUIRED_SCOPES
import com.rafaelwitak.spotifyltermobile.util.toast

internal var pkceClassBackTo: Class<out Activity>? = null

class SpotifyPkceLoginActivityImpl : AbstractSpotifyPkceLoginActivity() {
    override val clientId = BuildConfig.CLIENT_ID
    override val redirectUri = BuildConfig.REDIRECT_URI
    override val scopes = REQUIRED_SCOPES

    override fun onSuccess(api: SpotifyClientApi) {
        Model.credentialStore.setSpotifyApi(api)
        val classBackTo = pkceClassBackTo ?: MainActivity::class.java
        pkceClassBackTo = null
        toast("Authentication via PKCE has completed. " +
                "Launching ${classBackTo.simpleName}..")
        startActivity(Intent(this, classBackTo))
    }

    override fun onFailure(exception: Exception) {
        exception.printStackTrace()
        pkceClassBackTo = null
        toast("Auth failed: ${exception.message}")
    }
}