package com.flipperdevices.bcloudmock.dao.model

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import kotlinx.serialization.Serializable

@Serializable
data class UserWithTimestamp(
    val userObject: BSBApiUserObject,
    val firebaseTokens: List<String>,
    val timestamp: TimerTimestamp
)
