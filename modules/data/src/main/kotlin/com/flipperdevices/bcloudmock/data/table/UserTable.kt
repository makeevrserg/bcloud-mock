package com.flipperdevices.bcloudmock.data.table

import com.flipperdevices.bcloudmock.data.exposed.StringIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object UserTable : StringIdTable("USER", "uid") {
    val username = text("username").nullable()
    val displayName = text("display_name").nullable()
    val email = text("email")
    val createdAt = datetime("created_at")
    val updatedAt = datetime("updated_at")
}
