package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.AuthDataSource
import com.github.enteraname74.domain.model.User
import com.github.enteraname74.remotedatasource.model.RemoteToken
import com.github.enteraname74.remotedatasource.model.toRemoteUser
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import com.github.enteraname74.remotedatasource.utils.Token
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json

/**
 * Implementation of the AuthDataSource for remote data access.
 */
class RemoteAuthDataSourceImpl : AuthDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun authenticate(user: User) {
        try {
            val response = client.post(ServerRoutes.Auth.auth) {
                contentType(ContentType.Application.Json)
                setBody(user.toRemoteUser())
            }

            val token = response.body<RemoteToken>()
            Token.value = token.token

        } catch (_: Exception) {
        }
    }
}