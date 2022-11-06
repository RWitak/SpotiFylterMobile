package com.rafaelwitak.spotifyltermobile.util

private const val TRACK_PREFIX = "spotify:track:"

class TrackId(spotifyUri: String) {
    private val id: String

    init {
        require(spotifyUri.startsWith(TRACK_PREFIX)) {
            "Expected String with prefix '$TRACK_PREFIX', got: $spotifyUri"
        }
        id = spotifyUri.substring(TRACK_PREFIX.length)
    }

    override fun toString() = id

    override fun equals(other: Any?): Boolean {
        return if (other is TrackId) {
            id == other.id
        } else false
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
