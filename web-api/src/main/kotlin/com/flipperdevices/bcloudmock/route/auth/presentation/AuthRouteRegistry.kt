package com.flipperdevices.bcloudmock.route.auth.presentation

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class AuthRouteRegistry(
    private val dao: Dao,
) : RouteRegistry {
    private fun Routing.authRoute() {
        post(
            path = "auth",
            builder = { with(AuthSwagger) { createSwaggerDefinition() } },
            body = {
                val token = context.request.headers["Authorization"] ?: error("Token is required")
                val insertedUser = dao.insertUserToken(token).getOrThrow()
                context.respond(insertedUser)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
