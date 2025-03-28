package com.flipperdevices.bcloudmock.route.auth.presentation

import com.flipperdevices.bcloudmock.core.logging.Loggable
import com.flipperdevices.bcloudmock.core.logging.Slf4jLoggable
import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.route.timer.remember.presentation.TimerChangedController
import io.github.smiley4.ktoropenapi.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class AuthRouteRegistry(
    private val dao: Dao,
    private val timerChangedController: TimerChangedController
) : RouteRegistry, Loggable by Slf4jLoggable("AuthRouteRegistry") {
    private fun Routing.authRoute() {
        get(
            path = "/api/v0/auth",
            builder = { with(AuthSwagger) { createSwaggerDefinition() } },
            body = {
                val context = this.call
                val token = context.request.headers["Authorization"] ?: error("Token is required")
                val tokenFirebase = context.request.headers["Authorization-Firebase"]
                val insertedUser = dao.insertUserToken(token, tokenFirebase).getOrThrow()
                info { "#authRoute" }
                timerChangedController.timerChanged(insertedUser.uid)
                context.respond(insertedUser)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
