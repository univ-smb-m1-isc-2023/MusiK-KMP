package com.github.enteraname74.domain.model

/**
 * Represent a playlist with its information.
 */
data class Playlist(
    val id: String,
    val title: String,
    val musics: List<Music> // music not musics
)
