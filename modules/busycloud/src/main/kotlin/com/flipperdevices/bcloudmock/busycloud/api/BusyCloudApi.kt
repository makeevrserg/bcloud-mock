package com.flipperdevices.bcloudmock.busycloud.api

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject

interface BusyCloudApi {
    suspend fun authMe(token: String): Result<BSBApiUserObject>
}
