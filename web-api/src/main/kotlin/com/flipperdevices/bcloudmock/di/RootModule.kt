package com.flipperdevices.bcloudmock.di

import com.flipperdevices.bcloudmock.busycloud.di.BusyCloudModule
import com.flipperdevices.bcloudmock.core.buildkonfig.EnvKonfig
import com.flipperdevices.bcloudmock.core.di.CoreModule
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.data.di.DataModule
import com.flipperdevices.bcloudmock.push.di.PushModule
import com.flipperdevices.bcloudmock.route.auth.di.AuthModule
import com.flipperdevices.bcloudmock.route.timer.fetch.di.TimerFetchModule
import com.flipperdevices.bcloudmock.route.timer.remember.di.TimerRememberModuleModule

class RootModule {
    val coreModule = CoreModule.Default()
    private val databaseModule = DataModule(
        dbConnection = EnvKonfig.dbConnection
    )
    private val busyCloudModule = BusyCloudModule()
    private val daoModule = DaoModule(
        databaseModule = databaseModule,
        busyCloudModule = busyCloudModule,
        coreModule = coreModule
    )
    private val pushModule = PushModule()
    val authModule = AuthModule(
        daoModule = daoModule,
        pushModule = pushModule
    )
    val timerFetchModule = TimerFetchModule(
        daoModule = daoModule
    )
    val timerRememberModule = TimerRememberModuleModule(
        daoModule = daoModule,
        pushModule = pushModule
    )
}
