package com.rafaelwitak.spotifyltermobile.util

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.ContextCompat

class NetConnectCallbackImpl(
    application: Application,
    override val onConnected: () -> Unit = {},
    override val onDisconnected: () -> Unit = {},
    override val onCapabilityChanged: (Any?) -> Unit = {}
) : NetConnectCallback() {

    private val networkRequest: NetworkRequest by lazy {
        NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
    }

    private val networkCallback by lazy {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                onConnected()
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)

                val unmetered = networkCapabilities.hasCapability(
                    NetworkCapabilities.NET_CAPABILITY_NOT_METERED
                )
                onCapabilityChanged(
                    if (unmetered) Connection.UNMETERED else Connection.METERED
                )
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                onDisconnected()
            }
        }
    }
    private val connectivityManager by lazy {
        ContextCompat.getSystemService(
            application,
            ConnectivityManager::class.java
        ) as ConnectivityManager
    }

    fun activate() {
        connectivityManager.requestNetwork(networkRequest, networkCallback)
    }
}