package com.flipperdevices.bcloudmock.dao.api

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.dao.model.TimestampChangedEvent
import com.flipperdevices.bcloudmock.dao.model.UserWithTimestamp
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import kotlinx.coroutines.flow.Flow

interface Dao {
    val timestampChangedFlow: Flow<TimestampChangedEvent>

    suspend fun insertUserToken(token: String, tokenFirebase: String?): Result<BSBApiUserObject>

    suspend fun getUserByToken(token: String): Result<BSBApiUserObject>

    suspend fun saveTimestamp(token: String, timestamp: TimerTimestamp): Result<Unit>

    suspend fun readTimestampByToken(token: String): Result<TimerTimestamp>
    suspend fun readTimestampByUid(uid: String): Result<TimerTimestamp>

    suspend fun getUserTokenWithTimestamp(id: String): Result<UserWithTimestamp>
}
