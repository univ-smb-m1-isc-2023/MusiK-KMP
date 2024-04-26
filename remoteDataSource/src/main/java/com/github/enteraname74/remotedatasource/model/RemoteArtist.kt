package com.github.enteraname74.remotedatasource.model

import com.github.enteraname74.domain.model.Artist
import kotlinx.serialization.Serializable

@Serializable
data class RemoteArtist(
    val name: String,
    val musics: List<RemoteMusic>,
    val artworkUrl: String
)

internal fun RemoteArtist.toArtist() = Artist(
    name = name,
    musics = musics.map { it.toMusic() },
    artworkUrl = artworkUrl
)

internal fun Artist.toRemoteArtist() = RemoteArtist(
    name = name,
    musics = musics.map { it.toRemoteMusic() },
    artworkUrl = artworkUrl
)