package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.remotedatasource.model.RemoteMusic
import com.github.enteraname74.remotedatasource.model.toMusic
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import com.github.enteraname74.remotedatasource.utils.Token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json

/**
 * Implementation of the MusicInformationDataSource for remote data access.
 */
class RemoteMusicInformationDataSourceImpl: MusicInformationDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    override suspend fun getAll(): List<Music> {
        return try {
            println("Token: ${Token.value}")
            val response = client.get(ServerRoutes.MusicInformation.ALL) {
                header(HttpHeaders.Authorization, Token.value)
            }

            println("Status : ${response.status}")
            println("Body : ${response.bodyAsText()}")

            if (!response.status.isSuccess()) {
                emptyList()
            } else {
                val remoteMusicList: List<RemoteMusic> = response.body()
                remoteMusicList.map { it.toMusic() }
            }
        } catch (e: Exception) {
            println("EXCEPTION: $e")
            emptyList()
        }

    }
}