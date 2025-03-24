package com.flipperdevices.bcloudmock.busycloud.api

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.busycloud.model.BSBResponse
import com.flipperdevices.bcloudmock.busycloud.util.NetworkConstants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

internal class BusyCloudApiImpl(private val httpClient: HttpClient) : BusyCloudApi {
    override suspend fun authMe(
        token: String
    ): Result<BSBApiUserObject> = runCatching {
        httpClient.get {
            url("${NetworkConstants.BASE_URL}/v0/auth/me")
        }.body<BSBResponse<BSBApiUserObject>>()
    }.map { result -> result.success }
}
