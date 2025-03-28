@file:Suppress("Filename")

package com.flipperdevices.bcloudmock

import com.flipperdevices.bcloudmock.core.buildkonfig.EnvKonfig
import com.flipperdevices.bcloudmock.di.RootModule
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.logging.KtorSimpleLogger

fun main() {
    val rootModule = RootModule()
    val logger = KtorSimpleLogger("Application")
    logger.info("Starting...")
    logger.info("Server is running on port ${EnvKonfig.FBACKEND_PORT}")
    embeddedServer(
        factory = Netty,
        module = { module(rootModule) },
        port = EnvKonfig.FBACKEND_PORT,
        host = "192.168.0.108"
    ).start(wait = true)
}
