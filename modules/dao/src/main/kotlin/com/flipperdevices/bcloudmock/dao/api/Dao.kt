package com.flipperdevices.bcloudmock.dao.api

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.dao.model.UserWithTimestamp
import com.flipperdevices.bcloudmock.model.TimerTimestamp

interface Dao {
    /**
     * Inserts new token and fetch information about user
     */
    suspend fun insertUserToken(token: String, tokenFirebase: String?): Result<BSBApiUserObject>

    suspend fun getUserByToken(token: String): Result<BSBApiUserObject>

    suspend fun saveTimestamp(token: String, timestamp: TimerTimestamp): Result<Unit>

    suspend fun readTimestamp(token: String): Result<TimerTimestamp>

    suspend fun getUserTokenWithTimestamp(id: String): Result<UserWithTimestamp>
}
