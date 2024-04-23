package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.PlaylistDataSource
import com.github.enteraname74.domain.model.Playlist
import com.github.enteraname74.remotedatasource.model.RemotePlaylist
import com.github.enteraname74.remotedatasource.model.toPlaylist
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import com.github.enteraname74.remotedatasource.utils.Token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json

/**
 * Implementation of the PlaylistService for remote data access.
 */
class RemotePlaylistDataSourceImpl: PlaylistDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun getAll(): List<Playlist> {
        return try {
            val response = client.get(ServerRoutes.Playlist.ALL) {
                header(HttpHeaders.Authorization, Token.value)
            }

            if (!response.status.isSuccess()) {
                emptyList()
            } else {
                val remotePlaylists: List<RemotePlaylist> = response.body()
                remotePlaylists.map { it.toPlaylist() }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun get(id: String): Playlist? {
        return try {
            val response = client.get(ServerRoutes.MusicFile.get(id)) {
                header(HttpHeaders.Authorization, Token.value)
            }

            if (!response.status.isSuccess()) {
                null
            } else {
                val remotePlaylist: RemotePlaylist = response.body()
                remotePlaylist.toPlaylist()
            }
        } catch (_: Exception) {
            null
        }
    }
}
