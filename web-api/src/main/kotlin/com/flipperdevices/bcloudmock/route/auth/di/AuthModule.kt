package com.flipperdevices.bcloudmock.route.auth.di

import com.flipperdevices.bcloudmock.core.routing.RouteRegistry

interface AuthModule {
    val registry: RouteRegistry
}