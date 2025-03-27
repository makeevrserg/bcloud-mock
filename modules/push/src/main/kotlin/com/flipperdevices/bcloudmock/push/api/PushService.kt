package com.flipperdevices.bcloudmock.push.api

import com.flipperdevices.bcloudmock.push.model.FirebaseToken

interface PushService<T : FirebaseToken> {
    fun sendPush(token: T)
}
