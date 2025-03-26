package com.flipperdevices.bcloudmock

import com.flipperdevices.bcloudmock.di.RootModule
import com.flipperdevices.bcloudmock.plugins.configureHTTP
import com.flipperdevices.bcloudmock.plugins.configureSecurity
import com.flipperdevices.bcloudmock.plugins.configureSerialization
import com.flipperdevices.bcloudmock.plugins.configureStatusPages
import com.flipperdevices.bcloudmock.plugins.configureSwagger
import io.ktor.server.application.Application
import io.ktor.server.routing.routing
import org.slf4j.Logger

internal fun Application.module(rootModule: RootModule, logger: Logger) {
    configureSerialization(rootModule.coreModule.json)
    configureHTTP()
    configureSecurity()
    configureStatusPages()
    configureSwagger()
    routing {
        listOf(
            rootModule.authModule.registry,
            rootModule.timerFetchModule.registry,
            rootModule.timerRememberModule.registry
        ).forEach { routeRegistry -> routeRegistry.register(this) }
    }
    logger.info("Started!")
}
