package com.github.enteraname74.remotedatasource.serviceimpl

import com.github.enteraname74.domain.datasource.MusicInformationDataSource
import com.github.enteraname74.domain.model.Music
import com.github.enteraname74.remotedatasource.model.RemoteMusic
import com.github.enteraname74.remotedatasource.model.toMusic
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json

/**
 * Implementation of the MusicService for remote data access.
 */
class RemoteMusicInformationDataSourceImpl: MusicInformationDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }
    override suspend fun getAll(): List<Music> {
        val response = client.get(ServerRoutes.MusicInformation.ALL)

        return if (!response.status.isSuccess()) {
            emptyList()
        } else {
            val remoteMusicList: List<RemoteMusic> = response.body()
            remoteMusicList.map { it.toMusic() }
        }
    }
}