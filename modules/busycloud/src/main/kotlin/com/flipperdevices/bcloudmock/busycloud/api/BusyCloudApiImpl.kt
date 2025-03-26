package com.flipperdevices.bcloudmock.busycloud.api

import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.busycloud.model.BSBResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

internal class BusyCloudApiImpl(private val httpClient: HttpClient) : BusyCloudApi {
    override suspend fun authMe(
        token: String
    ): Result<BSBApiUserObject> = runCatching {
        BSBApiUserObject(
            uid = "some_uid",
            username = "username",
            displayName = "displayname",
            email = "enamil@mail.ru",
            createdAt = Instant.DISTANT_PAST.toLocalDateTime(TimeZone.UTC),
            updatedAt = Instant.DISTANT_PAST.toLocalDateTime(TimeZone.UTC)
        ).let(::BSBResponse)
//        httpClient.get {
//            url("${NetworkConstants.BASE_URL}/v0/auth/me")
//            header(HttpHeaders.ContentType, ContentType.Application.Json)
//            header("Authorization", token)
//        }.body<BSBResponse<BSBApiUserObject>>()
    }.map { result -> result.success }
}
