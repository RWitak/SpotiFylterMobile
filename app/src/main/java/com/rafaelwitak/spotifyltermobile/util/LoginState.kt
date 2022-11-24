package com.rafaelwitak.spotifyltermobile.util

sealed class LoginState {
    abstract val hasInternet: Boolean
    abstract fun copyWith(hasInternet: Boolean): LoginState

    data class Checking(override val hasInternet: Boolean) : LoginState() {
        override fun copyWith(hasInternet: Boolean) =
            copy(hasInternet = hasInternet)
    }

    data class Connected(override val hasInternet: Boolean) : LoginState() {
        override fun copyWith(hasInternet: Boolean) =
            copy(hasInternet = hasInternet)
    }

    data class AuthFailed(override val hasInternet: Boolean) : LoginState() {
        override fun copyWith(hasInternet: Boolean) =
            copy(hasInternet = hasInternet)
    }

    data class NotInstalled(override val hasInternet: Boolean) : LoginState() {
        override fun copyWith(hasInternet: Boolean) =
            copy(hasInternet = hasInternet)
    }
}
