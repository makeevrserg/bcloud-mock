package com.flipperdevices.bcloudmock.dao.api

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.model.TimerTimestamp

interface Dao {
    /**
     * Inserts new token and fetch information about user
     */
    suspend fun insertUserToken(token: String): Result<BSBApiUserObject>

    suspend fun getUserByToken(token: String): Result<BSBApiUserObject>

    suspend fun saveTimestamp(token: String, timestamp: TimerTimestamp): Result<Unit>
    suspend fun readTimestamp(token: String): Result<TimerTimestamp>
}
