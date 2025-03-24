package com.flipperdevices.bcloudmock.data.table

import org.jetbrains.exposed.dao.id.LongIdTable

object FirebaseTokenTable : LongIdTable("FIREBASE_TOKEN") {
    val user_id = reference("user_id", UserTable)
    val token = text("token")
}
