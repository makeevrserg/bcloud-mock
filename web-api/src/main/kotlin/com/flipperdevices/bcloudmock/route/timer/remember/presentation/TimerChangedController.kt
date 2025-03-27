package com.flipperdevices.bcloudmock.route.timer.remember.presentation

import com.flipperdevices.bcloudmock.dao.api.Dao
import com.flipperdevices.bcloudmock.push.api.AndroidPushService
import com.flipperdevices.bcloudmock.push.api.sendPush
import com.flipperdevices.bcloudmock.push.model.AndroidFirebaseToken
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class TimerChangedController(
    private val dao: Dao,
    private val androidPushService: AndroidPushService,
) {
    suspend fun timerChanged(uid: String) {
        supervisorScope {
            launch {
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
    }
}
