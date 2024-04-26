package com.github.enteraname74.remotedatasource.model

import com.github.enteraname74.domain.model.AlbumPreview
import kotlinx.serialization.Serializable

/**
 * Represent an AlbumPreview from the remote source.
 */
@Serializable
data class RemoteAlbumPreview(
    val albumName: String,
    val artistName: String,
    val totalSongs: Int,
    val artworkUrl: String = ""
)

/**
 * Converts a RemoteAlbumPreview to an AlbumPreview.
 */
fun RemoteAlbumPreview.toAlbumPreview(): AlbumPreview = AlbumPreview(
    albumName = albumName,
    artistName = artistName,
    totalSongs = totalSongs,
    artworkUrl = artworkUrl
)

/**
 * Converts an AlbumPreview to a RemoteAlbumPreview.
 */
fun AlbumPreview.toRemoteAlbumPreview(): RemoteAlbumPreview = RemoteAlbumPreview(
    albumName = albumName,
    artistName = artistName,
    totalSongs = totalSongs,
    artworkUrl = artworkUrl
)
