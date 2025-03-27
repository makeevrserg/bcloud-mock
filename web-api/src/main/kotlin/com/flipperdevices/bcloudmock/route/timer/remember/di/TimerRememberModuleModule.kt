package com.flipperdevices.bcloudmock.route.timer.remember.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.push.di.PushModule
import com.flipperdevices.bcloudmock.route.timer.remember.presentation.TimerRememberRouteRegistry

class TimerRememberModuleModule(
    daoModule: DaoModule,
    pushModule: PushModule
) {
    val registry: RouteRegistry = TimerRememberRouteRegistry(
        dao = daoModule.dao,
        androidPushService = pushModule.androidPushService
    )
}
