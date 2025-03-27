package com.flipperdevices.bcloudmock.route.timer.remember.presentation

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import com.flipperdevices.bcloudmock.push.api.AndroidPushService
import com.flipperdevices.bcloudmock.push.model.AndroidFirebaseToken
import io.github.smiley4.ktoropenapi.post
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class TimerRememberRouteRegistry(
    private val dao: Dao,
    private val androidPushService: AndroidPushService
) : RouteRegistry {
    private fun Routing.authRoute() {
        post(
            path = "/api/v0/timer/remember",
            builder = { with(TimerRememberSwagger) { createSwaggerDefinition() } },
            body = {
                val context = this.call
                val token = context.request.headers["Authorization"] ?: error("Token is required")
                val firebaseToken = context.request.headers["Authorization-Firebase"]
                val timestamp = context.receive<TimerTimestamp>()
                dao.saveTimestamp(token, timestamp).getOrThrow()
                val resultTimestamp = dao.readTimestamp(token).getOrThrow()
                dao.getUserTokenWithTimestamp(dao.getUserByToken(token).getOrThrow().uid)
                    .getOrNull()
                    ?.let { result ->
                        result.firebaseTokens.forEach { firebaseToken ->
                            val pushToken = AndroidFirebaseToken(firebaseToken)
                            androidPushService.sendPush(pushToken)
                        }
                    }
                context.respond(resultTimestamp)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
