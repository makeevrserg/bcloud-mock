package com.flipperdevices.bcloudmock.route.timer.remember.presentation

import com.flipperdevices.bcloudmock.core.logging.Loggable
import com.flipperdevices.bcloudmock.core.logging.Slf4jLoggable
import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import io.github.smiley4.ktoropenapi.post
import io.ktor.http.HttpHeaders
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class TimerRememberRouteRegistry(
    private val dao: Dao,
    private val timerChangedController: TimerChangedController
) : RouteRegistry, Loggable by Slf4jLoggable("AuthRouteRegistry") {
    private fun Routing.rememberTimerRoute() {
        post(
            path = "/api/v0/timer/remember",
            builder = { with(TimerRememberSwagger) { createSwaggerDefinition() } },
            body = {
                val context = this.call
                val token = context.request.headers[HttpHeaders.Authorization] ?: error("Token is required")
                val timestamp = context.receive<TimerTimestamp>()
                val lastTimestamp = dao.readTimestampByToken(token)
                    .getOrNull()
                    ?: TimerTimestamp.Pending.NotStarted

                if (lastTimestamp is TimerTimestamp.Pending && timestamp is TimerTimestamp.Pending) {
                    context.respond(lastTimestamp)
                } else if (lastTimestamp.lastSync == timestamp.lastSync) {
                    context.respond(lastTimestamp)
                } else if (lastTimestamp == timestamp) {
                    context.respond(lastTimestamp)
                } else if (timestamp.lastSync > lastTimestamp.lastSync) {
                    dao.saveTimestamp(token, timestamp).getOrThrow()
                    info { "#rememberTimerRoute" }
                    timerChangedController.timerChanged(uid = dao.getUserByToken(token).getOrThrow().uid)
                    context.respond(timestamp)
                } else {
                    context.respond(lastTimestamp)
                }
            }
        )
    }

    override fun register(routing: Routing) {
        routing.rememberTimerRoute()
    }
}
