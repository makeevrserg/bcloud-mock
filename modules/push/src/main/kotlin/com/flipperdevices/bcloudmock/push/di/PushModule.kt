package com.flipperdevices.bcloudmock.push.di

import com.flipperdevices.bcloudmock.core.buildkonfig.EnvKonfig
import com.flipperdevices.bcloudmock.core.di.CoreModule
import com.flipperdevices.bcloudmock.push.api.AndroidPushService
import com.flipperdevices.bcloudmock.push.api.ApplePushService
import com.flipperdevices.bcloudmock.push.model.FirebaseAppContext
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

class PushModule(coreModule: CoreModule) {
    private val firebaseAppContext = object : FirebaseAppContext {
        override val googleCredentials: GoogleCredentials = GoogleCredentials
            .fromStream(EnvKonfig.Firebase.PRIVATE_KEY.inputStream())
            .createScoped(listOf("https://www.googleapis.com/auth/firebase.messaging"))
        override val options: FirebaseOptions = FirebaseOptions.builder()
            .setCredentials(googleCredentials)
            .build()
        override val firebaseApp: FirebaseApp = FirebaseApp.initializeApp(options)
    }

    val androidPushService: AndroidPushService = AndroidPushService(firebaseAppContext, coreModule.json)
    val applePushService: ApplePushService = ApplePushService(firebaseAppContext)
}
