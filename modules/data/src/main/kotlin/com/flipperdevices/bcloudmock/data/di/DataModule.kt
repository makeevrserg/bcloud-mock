package com.flipperdevices.bcloudmock.data.di

import com.flipperdevices.bcloudmock.core.buildkonfig.model.DBConnection
import com.flipperdevices.bcloudmock.data.table.BCloudTokenTable
import com.flipperdevices.bcloudmock.data.table.FirebaseTokenTable
import com.flipperdevices.bcloudmock.data.table.UserTable
import kotlinx.coroutines.flow.flow
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

class DataModule(private val dbConnection: DBConnection) {
    val database = flow {
        val database = when (dbConnection) {
            is DBConnection.H2 -> Database.connect(
                url = "jdbc:h2:${dbConnection.path};DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false;MODE=MYSQL",
                driver = dbConnection.driver
            )
        }
        transaction(database) {
            addLogger(Slf4jSqlDebugLogger)
            SchemaUtils.create(
                BCloudTokenTable,
                FirebaseTokenTable,
                UserTable,
            )
        }
        emit(database)
    }
}
