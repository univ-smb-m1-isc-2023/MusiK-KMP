package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.AlbumDataSource
import com.github.enteraname74.domain.model.AlbumPreview
import com.github.enteraname74.remotedatasource.model.RemoteAlbumPreview
import com.github.enteraname74.remotedatasource.model.toAlbumPreview
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
 * Implementation of the AlbumService for remote data access.
 */
class RemoteAlbumDataSourceImpl : AlbumDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun getAll(): List<AlbumPreview> {
        return try {
            val response = client.get(ServerRoutes.Album.ALL) {
                header(HttpHeaders.Authorization, Token.value)
            }

            if (!response.status.isSuccess()) {
                emptyList()
            } else {
                val remoteAlbums: List<RemoteAlbumPreview> = response.body()
                remoteAlbums.map { it.toAlbumPreview() }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }
}
