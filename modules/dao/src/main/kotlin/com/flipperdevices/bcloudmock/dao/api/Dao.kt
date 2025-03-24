package com.flipperdevices.bcloudmock.dao.api

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject

interface Dao {
    /**
     * Inserts new token and fetch information about user
     */
    suspend fun insertUserToken(token: String): Result<Unit>

    suspend fun getUserByToken(token: String): Result<List<BSBApiUserObject>>
}
