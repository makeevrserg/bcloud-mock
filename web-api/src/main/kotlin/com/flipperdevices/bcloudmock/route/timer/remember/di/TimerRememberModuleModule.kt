package com.flipperdevices.bcloudmock.route.timer.remember.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.route.timer.remember.presentation.TimerRememberRouteRegistry

class TimerRememberModuleModule(daoModule: DaoModule) {
    val registry: RouteRegistry = TimerRememberRouteRegistry(daoModule.dao)
}
