package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.ArtistDataSource
import com.github.enteraname74.domain.model.Artist
import com.github.enteraname74.domain.model.ArtistPreview
import com.github.enteraname74.remotedatasource.model.RemoteArtist
import com.github.enteraname74.remotedatasource.model.RemoteArtistPreview
import com.github.enteraname74.remotedatasource.model.toArtist
import com.github.enteraname74.remotedatasource.model.toArtistPreview
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
 * Implementation of the ArtistService for remote data access.
 */
class RemoteArtistDataSourceImpl : ArtistDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun getAll(): List<ArtistPreview> {
        return try {
            val response = client.get(ServerRoutes.Artist.ALL) {
                header(HttpHeaders.Authorization, Token.value)
            }

            if (!response.status.isSuccess()) {
                emptyList()
            } else {
                val remoteArtists: List<RemoteArtistPreview> = response.body()
                remoteArtists.map { it.toArtistPreview() }
            }
        } catch (_: Exception) {
            emptyList()
        }
    }

    override suspend fun getByName(name: String): Artist? {
        return try {
            val response = client.get(ServerRoutes.Artist.get(name).replace(" ", "%20")) {
                header(HttpHeaders.Authorization, Token.value)
            }

            if (response.status.isSuccess()) {
                response.body<RemoteArtist>().toArtist()
            } else {
                null
            }
        } catch (_: Exception) {
            null
        }
    }
}
