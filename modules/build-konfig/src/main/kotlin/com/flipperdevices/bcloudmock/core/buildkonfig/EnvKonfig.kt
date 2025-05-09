package com.flipperdevices.bcloudmock.core.buildkonfig

import com.flipperdevices.bcloudmock.buildkonfig.BuildKonfig
import com.flipperdevices.bcloudmock.core.buildkonfig.model.DBConnection
import com.flipperdevices.bcloudmock.core.buildkonfig.model.DBType
import java.io.File
import java.net.InetAddress

object EnvKonfig {
    val FBACKEND_HOST: String
        get() = KSystem.getenvOrNull("FBACKEND_HOST")
            ?: InetAddress.getLocalHost().hostAddress

    val FBACKEND_PORT: Int
        get() = KSystem.getenvOrNull("FBACKEND_PORT")
            ?.toIntOrNull()
            ?: 8080

    private object DbKonfig {
        val FBACKEND_DB_TYPE: DBType
            get() = KSystem.getenvOrNull("FBACKEND_DB_TYPE")
                ?.let { type -> DBType.entries.firstOrNull { entry -> entry.name == type } }
                ?: DBType.H2

        val FBACKEND_DB_FULL_PATH: String
            get() = KSystem.getenvOrNull("DB_FULL_PATH")
                ?: BuildKonfig.FALLBACK_DB_FULL_PATH
    }

    object Firebase {
        val PRIVATE_KEY: File
            get() {
                val file = KSystem.getenvOrNull("PRIVATE_KEY_JSON_PATH")?.let(::File)
                    ?: File(BuildKonfig.PRIVATE_KEY_JSON_PATH)
                if (!file.exists() || file.length() == 0L) {
                    error("private-key.json not found!")
                }
                return file
            }
    }

    val dbConnection: DBConnection
        get() = when (DbKonfig.FBACKEND_DB_TYPE) {
            DBType.H2 -> DBConnection.H2(
                path = DbKonfig.FBACKEND_DB_FULL_PATH
            )
        }
}
