package com.flipperdevices.bcloudmock.push.api

import com.flipperdevices.bcloudmock.core.logging.Loggable
import com.flipperdevices.bcloudmock.core.logging.Slf4jLoggable
import com.flipperdevices.bcloudmock.push.model.AppleFirebaseToken
import com.flipperdevices.bcloudmock.push.model.FirebaseAppContext
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.ApnsConfig
import com.google.firebase.messaging.Aps
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message

class ApplePushService(
    private val firebaseAppContext: FirebaseAppContext
) : PushService<AppleFirebaseToken>,
    Loggable by Slf4jLoggable("IosPushService") {
    override fun sendPush(token: AppleFirebaseToken) {
        val message: Message = Message.builder()
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
            .setApnsConfig(
                ApnsConfig.builder()
                    .setAps(
                        Aps.builder()
                            .setContentAvailable(true) // For background notifications
                            .build()
                    )
                    .putHeader("apns-priority", "10")
                    .build()
            )
            .setToken(token.token)
            .putData("dnd", token.dnd.toString())
            .putData("id", token.id)
            .build()

        try {
            val response = FirebaseMessaging.getInstance(firebaseAppContext.firebaseApp).send(message)
            info { "Successfully sent message: $response" }
        } catch (e: Exception) {
            error(e) { "Failed to send message" }
        }
    }
}
