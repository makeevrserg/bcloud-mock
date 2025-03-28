package com.flipperdevices.bcloudmock

import com.flipperdevices.bcloudmock.di.RootModule
import com.flipperdevices.bcloudmock.plugins.configureHTTP
import com.flipperdevices.bcloudmock.plugins.configureSecurity
import com.flipperdevices.bcloudmock.plugins.configureSerialization
import com.flipperdevices.bcloudmock.plugins.configureStatusPages
import com.flipperdevices.bcloudmock.plugins.configureSwagger
import io.ktor.http.HttpHeaders
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.request.header
import io.ktor.server.routing.routing
import io.ktor.server.websocket.WebSockets
import io.ktor.server.websocket.webSocket
import io.ktor.websocket.CloseReason
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.send
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

internal fun Application.module(rootModule: RootModule) {
    configureSerialization(rootModule.coreModule.json)
    configureHTTP()
    configureSecurity()
    configureStatusPages()
    configureSwagger()
    install(WebSockets)
    routing {
        listOf(
            rootModule.authModule.registry,
            rootModule.timerFetchModule.registry,
            rootModule.timerRememberModule.registry
        ).forEach { routeRegistry -> routeRegistry.register(this) }

        webSocket("/api/v0/timersync") {
            val token = call.request.header(HttpHeaders.Authorization).orEmpty()
            if (token.isNullOrBlank()) {
                close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "Not authorized"))
            }
            try {
                println("starting timer job...")
                rootModule.daoModule.dao.timestampChangedFlow
                    .filter { it.user.uid == rootModule.daoModule.dao.getUserByToken(token).getOrNull()?.uid }
                    .onEach {
                        send(rootModule.coreModule.json.encodeToString(it.timestamp))
                    }
                    .launchIn(this)

                println("reading timestamp...")
                rootModule.daoModule.dao.readTimestampByToken(token)
                    .getOrNull()
                    ?.let { timestamp ->
                        send(rootModule.coreModule.json.encodeToString(timestamp))
                    }

                println("consume flow...")
                incoming.consumeAsFlow().onEach {
                    when (it) {
                        is Frame.Binary -> println("#onCreate client -> Binary")
                        is Frame.Close -> println("#onCreate client -> Close")
                        is Frame.Ping -> println("#onCreate client -> Ping")
                        is Frame.Pong -> println("#onCreate client -> Pong")
                        is Frame.Text -> println("#onCreate client -> Text -> ${it.data.decodeToString()}")
                    }
                }.launchIn(this)
                while (true) {
                    delay(1000L)
                }
            } catch (_: ClosedReceiveChannelException) {
                println("onClose ${closeReason.await()}")
            } catch (e: Throwable) {
                println("onError ${closeReason.await()}")
                e.printStackTrace()
            }
        }
    }
}
