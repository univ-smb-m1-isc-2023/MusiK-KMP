package com.github.enteraname74.remotedatasource.model

import com.github.enteraname74.domain.model.Music
import kotlinx.serialization.Serializable

/**
 * Represent a Music from the remote source.
 */
@Serializable
data class RemoteMusic(
    val id: String,
    val name: String,
    val artist: String,
    val album: String,
    val albumArtworkUrl: String = "",
    val playlistIds: List<String> = emptyList()
)

/**
 * Converts a RemoteMusic to a Music.
 */
fun RemoteMusic.toMusic(): Music = Music(
    id = id,
    name = name,
    artist = artist,
    album = album,
    albumArtworkUrl = albumArtworkUrl,
    playlistIds = playlistIds
)

/**
 * Converts a Music to a RemoteMusic.
 */
fun Music.toRemoteMusic(): RemoteMusic = RemoteMusic(
    id = id,
    name = name,
    artist = artist,
    album = album,
    albumArtworkUrl = albumArtworkUrl,
    playlistIds = playlistIds
)
