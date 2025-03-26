package com.flipperdevices.bcloudmock.route.timer.remember.presentation

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import io.github.smiley4.ktoropenapi.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class TimerRememberRouteRegistry(
    private val dao: Dao,
) : RouteRegistry {
    private fun Routing.authRoute() {
        post(
            path = "/api/v0/timer/remember",
            builder = { with(TimerRememberSwagger) { createSwaggerDefinition() } },
            body = {
                val context = this.call
                val token = context.request.headers["Authorization"] ?: error("Token is required")
                val timestamp = context.receive<TimerTimestamp>()
                dao.saveTimestamp(token, timestamp).getOrThrow()
                val resultTimestamp = dao.readTimestamp(token).getOrThrow()
                context.respond(resultTimestamp)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
