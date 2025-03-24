package com.flipperdevices.bcloudmock.dao.api

import com.flipperdevices.bcloudmock.busycloud.api.BusyCloudApi
import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.data.table.FirebaseTokenTable
import com.flipperdevices.bcloudmock.data.table.UserTable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

internal class DaoImpl(
    private val busyCloudApi: BusyCloudApi,
    private val databaseFlow: Flow<Database>
) : Dao {
    private suspend fun requireDatabase(): Database {
        return databaseFlow.first()
    }

    override suspend fun insertUserToken(
        token: String
    ): Result<Unit> = runCatching {
        val user = busyCloudApi.authMe(token).getOrThrow()

        transaction(requireDatabase()) {
            val id = UserTable.insertAndGetId {
                it[UserTable.id] = user.uid
                it[UserTable.username] = user.username
                it[UserTable.displayName] = user.displayName
                it[UserTable.email] = user.email
                it[UserTable.createdAt] = user.createdAt.toJavaLocalDateTime()
                it[UserTable.updatedAt] = user.updatedAt.toJavaLocalDateTime()
            }.value

            FirebaseTokenTable.insert {
                it[FirebaseTokenTable.token] = token
                it[FirebaseTokenTable.user_id] = id
            }
        }
    }

    override suspend fun getUserByToken(token: String): Result<List<BSBApiUserObject>> {
        return runCatching {
            transaction(requireDatabase()) {
                val userIds = FirebaseTokenTable.select(FirebaseTokenTable.user_id)
                    .where { FirebaseTokenTable.token eq token }
                    .map { resultRow -> resultRow[FirebaseTokenTable.user_id].value }

                UserTable.selectAll()
                    .where { UserTable.id inList userIds }
                    .map { resultRow ->
                        BSBApiUserObject(
                            uid = resultRow[UserTable.id].value,
                            username = resultRow[UserTable.username],
                            displayName = resultRow[UserTable.displayName],
                            email = resultRow[UserTable.email],
                            createdAt = resultRow[UserTable.createdAt].toKotlinLocalDateTime(),
                            updatedAt = resultRow[UserTable.updatedAt].toKotlinLocalDateTime(),
                        )
                    }
            }
        }
    }
}
