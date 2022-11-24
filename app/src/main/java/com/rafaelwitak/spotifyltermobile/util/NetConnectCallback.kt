package com.rafaelwitak.spotifyltermobile.util

abstract class NetConnectCallback {
    protected abstract val onConnected: () -> Unit
    protected abstract val onDisconnected: () -> Unit
    protected abstract val onCapabilityChanged: (Connection) -> Unit

    enum class Connection {
        METERED,
        UNMETERED
    }
}