package com.flipperdevices.bcloudmock.dao.model

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.model.TimerTimestamp

data class TimestampChangedEvent(
    val user: BSBApiUserObject,
    val timestamp: TimerTimestamp
)
