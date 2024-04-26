package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.LyricsDataSource
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import com.github.enteraname74.remotedatasource.utils.Token
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.isSuccess

/**
 * Implementation of the LyricsDataSource for remote data access.
 */
class RemoteLyricsDataSourceImpl: LyricsDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun fetchLyrics(musicId: String): String {
        return try {
            val response = client.get(ServerRoutes.Lyrics.get(musicId)) {
                header(HttpHeaders.Authorization, Token.value)
            }

            if (response.status.isSuccess()) response.bodyAsText() else ""
        } catch (_: Exception) {
            ""
        }
    }
}