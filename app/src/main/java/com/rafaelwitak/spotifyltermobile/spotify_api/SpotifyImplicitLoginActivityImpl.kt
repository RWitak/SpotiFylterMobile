package com.rafaelwitak.spotifyltermobile.spotify_api

import android.content.Intent
import com.adamratzman.spotify.SpotifyImplicitGrantApi
import com.adamratzman.spotify.SpotifyScope
import com.adamratzman.spotify.auth.implicit.AbstractSpotifyAppImplicitLoginActivity
import com.rafaelwitak.spotifyltermobile.BuildConfig
import com.rafaelwitak.spotifyltermobile.gui.MainActivity
import com.rafaelwitak.spotifyltermobile.model.Model
import com.rafaelwitak.spotifyltermobile.util.toast

class SpotifyImplicitLoginActivityImpl :
    AbstractSpotifyAppImplicitLoginActivity() {
    override val state: Int = 1337
    override val clientId: String = BuildConfig.CLIENT_ID
    override val redirectUri: String = BuildConfig.REDIRECT_URI
    override val useDefaultRedirectHandler: Boolean = false
    override fun getRequestingScopes(): List<SpotifyScope> =
        SpotifyScope.values().toList()

    override fun onSuccess(spotifyApi: SpotifyImplicitGrantApi) {
        Model.credentialStore.setSpotifyApi(spotifyApi)
        toast("Authentication successful!")
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun onFailure(errorMessage: String) {
        toast("Authentication failed: $errorMessage")
    }
}
