package com.github.enteraname74.domain.model

/**
 * Represent an album preview.
 */
data class AlbumPreview(
    val albumName: String,
    val artistName: String,
    val totalSongs: Int,
    val artworkUrl: String,
)
