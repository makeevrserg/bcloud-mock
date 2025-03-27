package com.flipperdevices.bcloudmock.push.model

data class AppleFirebaseToken(val token: String, val dnd: Boolean, val id: String) : FirebaseToken
