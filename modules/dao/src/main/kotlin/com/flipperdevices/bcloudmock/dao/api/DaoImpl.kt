package com.flipperdevices.bcloudmock.dao.api

import com.flipperdevices.bcloudmock.buildkonfig.BuildKonfig
import com.flipperdevices.bcloudmock.busycloud.api.BusyCloudApi
import com.flipperdevices.bcloudmock.busycloud.model.BSBApiUserObject
import com.flipperdevices.bcloudmock.dao.model.UserWithTimestamp
import com.flipperdevices.bcloudmock.data.table.BCloudTokenTable
import com.flipperdevices.bcloudmock.data.table.FirebaseTokenTable
import com.flipperdevices.bcloudmock.data.table.UserTable
import com.flipperdevices.bcloudmock.model.TimerTimestamp
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import kotlinx.serialization.StringFormat
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.insertIgnore
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import ru.astrainteractive.astralibs.serialization.StringFormatExt.parseOrDefault
import ru.astrainteractive.astralibs.serialization.StringFormatExt.writeIntoFile
import java.io.File

internal class DaoImpl(
    private val busyCloudApi: BusyCloudApi,
    private val databaseFlow: Flow<Database>,
    private val stringFormat: StringFormat
) : Dao {
    private suspend fun requireDatabase(): Database {
        return databaseFlow.first()
    }

    override suspend fun insertUserToken(
        token: String,
        tokenFirebase: String?
    ): Result<BSBApiUserObject> = runCatching {
        // Don't add same token twice
        val user = getUserByToken(token).getOrNull() ?: busyCloudApi.authMe(token).getOrThrow()

        transaction(requireDatabase()) {
            val existedUser = UserTable.select(UserTable.id)
                .where { UserTable.id eq user.uid }
                .map { it[UserTable.id] }
                .firstOrNull()
                ?.value
            val id = existedUser ?: UserTable.insertAndGetId {
                it[UserTable.id] = user.uid
                it[UserTable.username] = user.username
                it[UserTable.displayName] = user.displayName
                it[UserTable.email] = user.email
                it[UserTable.createdAt] = user.createdAt.toJavaLocalDateTime()
                it[UserTable.updatedAt] = user.updatedAt.toJavaLocalDateTime()
            }.value

            BCloudTokenTable.insertIgnore {
                it[BCloudTokenTable.token] = token
                it[BCloudTokenTable.user_id] = id
            }
            tokenFirebase?.let {
                FirebaseTokenTable.insertIgnore {
                    it[FirebaseTokenTable.token] = tokenFirebase
                    it[FirebaseTokenTable.user_id] = id
                }
            }
        }
        user
    }

    private suspend fun getUserById(id: String): Result<BSBApiUserObject> {
        return runCatching {
            transaction(requireDatabase()) {
                UserTable.selectAll()
                    .where { UserTable.id eq id }
                    .map { resultRow ->
                        BSBApiUserObject(
                            uid = resultRow[UserTable.id].value,
                            username = resultRow[UserTable.username],
                            displayName = resultRow[UserTable.displayName],
                            email = resultRow[UserTable.email],
                            createdAt = resultRow[UserTable.createdAt].toKotlinLocalDateTime(),
                            updatedAt = resultRow[UserTable.updatedAt].toKotlinLocalDateTime(),
                        )
                    }.first()
            }
        }
    }

    override suspend fun getUserByToken(token: String): Result<BSBApiUserObject> {
        return runCatching {
            val userId = transaction(requireDatabase()) {
                BCloudTokenTable.select(BCloudTokenTable.user_id)
                    .where { BCloudTokenTable.token eq token }
                    .map { resultRow -> resultRow[BCloudTokenTable.user_id].value }
                    .first()
            }
            getUserById(userId).getOrThrow()
        }
    }

    private val BSBApiUserObject.timestampFile: File
        get() {
            val file = File(BuildKonfig.UID_TIMER_DATA_PATH).resolve("$uid.json")
            file.parentFile.mkdirs()
            return file
        }

    override suspend fun saveTimestamp(token: String, timestamp: TimerTimestamp): Result<Unit> {
        return runCatching {
            val user = getUserByToken(token).getOrNull() ?: insertUserToken(token, null).getOrThrow()
            stringFormat.writeIntoFile(timestamp, user.timestampFile)
        }
    }

    override suspend fun readTimestamp(token: String): Result<TimerTimestamp> {
        return runCatching {
            val user = getUserByToken(token).getOrNull() ?: insertUserToken(token, null).getOrThrow()
            stringFormat.parseOrDefault<TimerTimestamp>(user.timestampFile) {
                TimerTimestamp.Pending.NotStarted
            }
        }
    }

    override suspend fun getUserTokenWithTimestamp(id: String): Result<UserWithTimestamp> {
        return runCatching {
            val user = getUserById(id).getOrThrow()
            val tokensFirebase = transaction(requireDatabase()) {
                FirebaseTokenTable.select(FirebaseTokenTable.token)
                    .where { FirebaseTokenTable.user_id eq id }
                    .map { it[FirebaseTokenTable.token] }

            }
            val timestamp = stringFormat.parseOrDefault<TimerTimestamp>(user.timestampFile) {
                TimerTimestamp.Pending.NotStarted
            }
            UserWithTimestamp(
                userObject = user,
                timestamp = timestamp,
                firebaseTokens = tokensFirebase
            )
        }
    }
}
