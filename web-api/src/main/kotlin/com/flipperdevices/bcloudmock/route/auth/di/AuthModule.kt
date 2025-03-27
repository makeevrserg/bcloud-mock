package com.flipperdevices.bcloudmock.route.auth.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry
import com.flipperdevices.bcloudmock.dao.di.DaoModule
import com.flipperdevices.bcloudmock.push.di.PushModule
import com.flipperdevices.bcloudmock.route.auth.presentation.AuthRouteRegistry

class AuthModule(
    daoModule: DaoModule,
    pushModule: PushModule
) {
    val registry: RouteRegistry = AuthRouteRegistry(
        dao = daoModule.dao,
        androidPushService = pushModule.androidPushService
    )
}
