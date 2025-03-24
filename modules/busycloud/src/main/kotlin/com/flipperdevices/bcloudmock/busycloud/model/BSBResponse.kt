package com.flipperdevices.bcloudmock.busycloud.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BSBResponse<T>(
    @SerialName("success")
    val success: T,
)
