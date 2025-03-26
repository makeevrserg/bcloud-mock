package com.flipperdevices.bcloudmock.model

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val token: String
)