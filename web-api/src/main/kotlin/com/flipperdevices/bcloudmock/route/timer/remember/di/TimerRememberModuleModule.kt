package com.flipperdevices.bcloudmock.route.timer.remember.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.route.timer.remember.presentation.TimerChangedController
import com.flipperdevices.bcloudmock.route.timer.remember.presentation.TimerRememberRouteRegistry

class TimerRememberModuleModule(
    daoModule: DaoModule,
    timerChangedController: TimerChangedController
) {
    val registry: RouteRegistry = TimerRememberRouteRegistry(
        dao = daoModule.dao,
        timerChangedController = timerChangedController
    )
}
