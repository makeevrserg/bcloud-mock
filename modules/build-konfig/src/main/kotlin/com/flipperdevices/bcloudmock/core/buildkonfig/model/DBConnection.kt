package com.flipperdevices.bcloudmock.core.buildkonfig.model

sealed class DBConnection(val driver: String) {
    class H2(val path: String) : DBConnection("org.h2.Driver")
}
