package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.UserDataSource
import com.github.enteraname74.remotedatasource.model.RemoteUser
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json

/**
 * Implementation of the UserDataSource for remote data access.
 */
class RemoteUserDataSourceImpl : UserDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun create(username: String, password: String): Boolean {
        return try {
            val user = RemoteUser(username, password)
            val response = client.post(ServerRoutes.User.connect) {
                contentType(ContentType.Application.Json)
                setBody(user)
            }
            response.status.isSuccess()
        } catch (e: Exception) {
            false
        }
    }
}