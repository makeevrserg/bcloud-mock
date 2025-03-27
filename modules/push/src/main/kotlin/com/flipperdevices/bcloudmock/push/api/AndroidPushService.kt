package com.flipperdevices.bcloudmock.push.api

import com.flipperdevices.bcloudmock.core.logging.Loggable
import com.flipperdevices.bcloudmock.core.logging.Slf4jLoggable
import com.flipperdevices.bcloudmock.push.model.AndroidFirebaseToken
import com.flipperdevices.bcloudmock.push.model.FirebaseAppContext
import com.google.firebase.messaging.AndroidConfig
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.Message
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json

class AndroidPushService(
    private val firebaseAppContext: FirebaseAppContext,
    private val json: Json
) : PushService<AndroidFirebaseToken>,
    Loggable by Slf4jLoggable("AndroidPushService") {
    override fun <K> sendPush(
        token: AndroidFirebaseToken,
        data: K,
        serializer: KSerializer<K>
    ) {
        info { "#sendPush $token data: $data" }
        val message: Message = Message.builder()
            .putData("data", json.encodeToString(serializer, data))
            .setAndroidConfig(
                AndroidConfig.builder()
                    .setPriority(AndroidConfig.Priority.HIGH)
                    .build()
            )
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
