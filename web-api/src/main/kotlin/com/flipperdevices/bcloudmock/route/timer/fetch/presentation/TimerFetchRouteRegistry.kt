package com.flipperdevices.bcloudmock.route.timer.fetch.presentation

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import io.github.smiley4.ktoropenapi.get
import io.ktor.http.HttpHeaders
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class TimerFetchRouteRegistry(
    private val dao: Dao,
) : RouteRegistry {
    private fun Routing.authRoute() {
        get(
            path = "/api/v0/timer/read",
            builder = { with(TimerFetchSwagger) { createSwaggerDefinition() } },
            body = {
                val context = this.call
                val token = context.request.headers[HttpHeaders.Authorization] ?: error("Token is required")
                val resultTimestamp = dao.readTimestampByToken(token).getOrThrow()
                context.respond(resultTimestamp)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
