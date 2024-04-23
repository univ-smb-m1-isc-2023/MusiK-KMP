package com.github.enteraname74.remotedatasource.datasourceimpl

import com.github.enteraname74.domain.datasource.MusicFileDataSource
import com.github.enteraname74.domain.model.File
import com.github.enteraname74.remotedatasource.utils.ServerRoutes
import com.github.enteraname74.remotedatasource.utils.Token
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.forms.InputProvider
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.isSuccess
import io.ktor.http.quote
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.streams.asInput

/**
 * Implementation of the MusicFileDataSource for remote data access.
 */
class RemoteMusicFileDataSourceImpl : MusicFileDataSource {
    private val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json()
        }
    }

    override suspend fun uploadFile(file: File): Boolean {
        println("File name:" + file.name)

        return try {
            val response = client.submitFormWithBinaryData(
                url = ServerRoutes.MusicFile.UPLOAD,
                formData = formData {
                    append(
                        "file".quote(),
                        InputProvider(file.length) { file.inputStream.asInput() },
                        Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${file.name}"
                            )
                            append(HttpHeaders.ContentType, file.contentType)
                        }
                    )
                }
            ) {
                header(HttpHeaders.Authorization, Token.value)
            }
            println("Response from sending file: ${response.status}")
            println("Response from sending file: ${response.bodyAsText()}")
            response.status.isSuccess()
        } catch (e: Exception) {
            println("Error: $e")
            e.stackTrace.forEach {
                println(it.toString())
            }
            false
        }
    }
}