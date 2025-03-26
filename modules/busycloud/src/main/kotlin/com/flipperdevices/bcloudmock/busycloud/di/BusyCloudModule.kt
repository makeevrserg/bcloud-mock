package com.flipperdevices.bcloudmock.busycloud.di

import com.flipperdevices.bcloudmock.busycloud.api.BusyCloudApi
import com.flipperdevices.bcloudmock.busycloud.api.BusyCloudApiImpl
import com.flipperdevices.bcloudmock.core.logging.Slf4jLoggable
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class BusyCloudModule {
    private val json = Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        coerceInputValues = true
    }
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(json)
        }

        install(Logging) {
            val slf4jLoggable = Slf4jLoggable("HttpClient")
            logger = object : Logger {
                override fun log(message: String) {
                    slf4jLoggable.info { message }
                }
            }
            level = LogLevel.ALL
        }

        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
    }
    val api: BusyCloudApi = BusyCloudApiImpl(httpClient)
}
