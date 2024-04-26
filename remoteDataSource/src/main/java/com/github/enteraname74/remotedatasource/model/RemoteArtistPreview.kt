package com.github.enteraname74.remotedatasource.model

import com.github.enteraname74.domain.model.ArtistPreview
import kotlinx.serialization.Serializable

/**
 * Represent an ArtistPreview from the remote source.
 */
@Serializable
data class RemoteArtistPreview(
    val name: String,
    val totalSongs: Int,
    val artworkUrl: String = ""
)

/**
 * Converts a RemoteArtistPreview to an ArtistPreview.
 */
fun RemoteArtistPreview.toArtistPreview(): ArtistPreview = ArtistPreview(
    name = name,
    totalSongs = totalSongs,
    artworkUrl = artworkUrl
)

/**
 * Converts an ArtistPreview to a RemoteArtistPreview.
 */
fun ArtistPreview.toRemoteArtistPreview(): RemoteArtistPreview = RemoteArtistPreview(
    name = name,
    totalSongs = totalSongs,
    artworkUrl = artworkUrl
)
