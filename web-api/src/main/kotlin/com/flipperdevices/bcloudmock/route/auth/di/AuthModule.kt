package com.flipperdevices.bcloudmock.route.auth.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.route.auth.presentation.AuthRouteRegistry

class AuthModule(daoModule: DaoModule) {
    val registry: RouteRegistry = AuthRouteRegistry(daoModule.dao)
}
