package com.github.enteraname74.remotedatasource.model

import com.github.enteraname74.domain.model.Playlist
import kotlinx.serialization.Serializable

/**
 * Represents a Playlist from the remote source.
 */
@Serializable
data class RemotePlaylist(
    val id: String,
    val title: String,
    val musics: List<RemoteMusic> // music not musics
)

/**
 * Converts a RemotePlaylist to a Playlist.
 */
fun RemotePlaylist.toPlaylist(): Playlist = Playlist(
    id = id,
    title = title,
    musics = musics.map { it.toMusic() } // music not musics
)

/**
 * Converts a Playlist to a RemotePlaylist.
 */
fun Playlist.toRemotePlaylist(): RemotePlaylist = RemotePlaylist(
    id = id,
    title = title,
    musics = musics.map { it.toRemoteMusic() }
)
