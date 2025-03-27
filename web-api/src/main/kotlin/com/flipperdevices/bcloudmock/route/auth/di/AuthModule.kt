package com.flipperdevices.bcloudmock.route.auth.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.route.auth.presentation.AuthRouteRegistry
import com.flipperdevices.bcloudmock.route.timer.remember.presentation.TimerChangedController

class AuthModule(
    daoModule: DaoModule,
    timerChangedController: TimerChangedController
) {
    val registry: RouteRegistry = AuthRouteRegistry(
        dao = daoModule.dao,
        timerChangedController = timerChangedController
    )
}
