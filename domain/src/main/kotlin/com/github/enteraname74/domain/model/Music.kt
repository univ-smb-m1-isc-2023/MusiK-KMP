package com.github.enteraname74.domain.model

/**
 * Represent a music with its information.
 */
data class Music(
    val id: String,
    val name: String,
    val artist: String,
    val album: String,
    val albumArtworkUrl: String = ""
)
