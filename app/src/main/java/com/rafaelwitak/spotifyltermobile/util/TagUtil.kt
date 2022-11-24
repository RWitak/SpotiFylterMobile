package com.rafaelwitak.spotifyltermobile.util

val Any.TAG: String
    get() = if (!javaClass.isAnonymousClass) {
        javaClass.simpleName
    } else {
        javaClass.name
    }