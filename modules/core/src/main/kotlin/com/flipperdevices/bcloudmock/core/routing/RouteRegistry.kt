package com.flipperdevices.bcloudmock.core.routing

import io.ktor.server.routing.Routing

fun interface RouteRegistry {
    fun register(routing: Routing)
}
