package com.flipperdevices.bcloudmock.data.exposed

import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.Column

open class StringIdTable(name: String = "", columnName: String = "id") : IdTable<String>(name) {
    final override val id: Column<EntityID<String>> = text(columnName).entityId()
    final override val primaryKey = PrimaryKey(id)
}
