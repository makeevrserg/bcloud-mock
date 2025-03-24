package com.flipperdevices.bcloudmock.route.auth.presentation

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.routing.Routing

class AuthRouteRegistry : RouteRegistry {
    private fun Routing.authRoute() {
        post(
            path = "auth",
            builder = { with(AuthSwagger) { createSwaggerDefinition() } },
            body = {
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
