package com.flipperdevices.bcloudmock.route.timer.fetch.presentation

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import io.github.smiley4.ktorswaggerui.dsl.routing.post
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class TimerFetchRouteRegistry(
    private val dao: Dao,
) : RouteRegistry {
    private fun Routing.authRoute() {
        post(
            path = "/api/v0/timer/read",
            builder = { with(TimerFetchSwagger) { createSwaggerDefinition() } },
            body = {
                val token = context.request.headers["Authorization"] ?: error("Token is required")
                val resultTimestamp = dao.readTimestamp(token).getOrThrow()
                context.respond(resultTimestamp)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
