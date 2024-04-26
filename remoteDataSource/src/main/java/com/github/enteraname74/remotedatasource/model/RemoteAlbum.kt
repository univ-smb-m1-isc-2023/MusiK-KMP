package com.github.enteraname74.remotedatasource.model

import com.github.enteraname74.domain.model.Album
import com.github.enteraname74.domain.model.Music
import kotlinx.serialization.Serializable

@Serializable
data class RemoteAlbum(
    val name: String,
    val artist: String,
    val musics: List<RemoteMusic>,
    val artworkUrl: String
)

internal fun RemoteAlbum.toAlbum() = Album(
    name = name,
    artist = artist,
    musics = musics.map { it.toMusic() },
    artworkUrl = artworkUrl
)

internal fun Album.toRemoteAlbum() = RemoteAlbum(
    name = name,
    artist = artist,
    musics = musics.map { it.toRemoteMusic() },
    artworkUrl = artworkUrl
)
