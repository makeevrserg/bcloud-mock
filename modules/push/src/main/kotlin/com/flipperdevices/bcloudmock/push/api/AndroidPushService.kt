package com.flipperdevices.bcloudmock.push.api

import com.flipperdevices.bcloudmock.core.logging.Loggable
import com.flipperdevices.bcloudmock.core.logging.Slf4jLoggable
import com.flipperdevices.bcloudmock.push.model.AndroidFirebaseToken
import com.flipperdevices.bcloudmock.push.model.FirebaseAppContext
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import com.google.firebase.messaging.Notification

class AndroidPushService(
    private val firebaseAppContext: FirebaseAppContext
) : PushService<AndroidFirebaseToken>,
    Loggable by Slf4jLoggable("AndroidPushService") {

    override fun sendPush(token: AndroidFirebaseToken) {
        info { "#sendPush $token" }
        val message: Message = Message.builder()
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
            .setNotification(Notification.builder().setTitle("Hello!").build())
            .setToken(token.value)
            .build()

        try {
            val response = FirebaseMessaging.getInstance(firebaseAppContext.firebaseApp).send(message)
            info { "Successfully sent message: $response" }
        } catch (e: Exception) {
            error(e) { "Failed to send message" }
        }
    }
}
