package com.flipperdevices.bcloudmock.route.timer.remember.presentation

import com.flipperdevices.bcloudmock.core.logging.Loggable
import com.flipperdevices.bcloudmock.core.logging.Slf4jLoggable
import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.push.api.AndroidPushService
import com.flipperdevices.bcloudmock.push.api.sendPush
import com.flipperdevices.bcloudmock.push.model.AndroidFirebaseToken

class TimerChangedController(
    private val dao: Dao,
    private val androidPushService: AndroidPushService,
) : Loggable by Slf4jLoggable("TimerChangedController") {
    suspend fun timerChanged(uid: String) {
        info { "#timerChanged $uid" }
        val timestamp = dao.readTimestampByUid(uid).getOrThrow()
        dao.getUserTokenWithTimestamp(uid)
            .getOrNull()
            ?.let { result ->
                result.firebaseTokens.forEach { firebaseToken ->
                    val pushToken = AndroidFirebaseToken(firebaseToken)
                    androidPushService.sendPush(pushToken, timestamp)
                }
            }
    }
}
