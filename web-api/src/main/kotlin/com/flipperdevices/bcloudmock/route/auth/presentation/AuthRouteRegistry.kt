package com.flipperdevices.bcloudmock.route.auth.presentation

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.push.api.AndroidPushService
import com.flipperdevices.bcloudmock.push.model.AndroidFirebaseToken
import io.github.smiley4.ktoropenapi.get
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing

class AuthRouteRegistry(
    private val dao: Dao,
    private val androidPushService: AndroidPushService
) : RouteRegistry {
    private fun Routing.authRoute() {
        get(
            path = "/api/v0/auth",
            builder = { with(AuthSwagger) { createSwaggerDefinition() } },
            body = {
                val context = this.call
                val token = context.request.headers["Authorization"] ?: error("Token is required")
                val tokenFirebase = context.request.headers["Authorization-Firebase"] ?: error("Token is required")
                val insertedUser = dao.insertUserToken(token, tokenFirebase).getOrThrow()
                dao.getUserTokenWithTimestamp(dao.getUserByToken(token).getOrThrow().uid)
                    .getOrNull()
                    ?.let { result ->
                        result.firebaseTokens.forEach { firebaseToken ->
                            val pushToken = AndroidFirebaseToken(firebaseToken)
                            androidPushService.sendPush(pushToken)
                        }
                    }
                context.respond(insertedUser)
            }
        )
    }

    override fun register(routing: Routing) {
        routing.authRoute()
    }
}
