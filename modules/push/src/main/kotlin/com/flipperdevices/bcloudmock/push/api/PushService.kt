package com.flipperdevices.bcloudmock.push.api

import com.flipperdevices.bcloudmock.push.model.FirebaseToken
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer

interface PushService<T : FirebaseToken> {
    fun <K> sendPush(token: T, data: K, serializer: KSerializer<K>)
}

inline fun <reified K, T : FirebaseToken> PushService<T>.sendPush(
    token: T,
    data: K,
) {
    return sendPush(
        token = token,
        data = data,
        serializer = serializer()
    )
}
