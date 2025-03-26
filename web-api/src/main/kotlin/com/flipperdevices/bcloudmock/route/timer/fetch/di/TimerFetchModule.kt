package com.flipperdevices.bcloudmock.route.timer.fetch.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.route.timer.fetch.presentation.TimerFetchRouteRegistry

class TimerFetchModule(daoModule: DaoModule) {
    val registry: RouteRegistry = TimerFetchRouteRegistry(daoModule.dao)
}
