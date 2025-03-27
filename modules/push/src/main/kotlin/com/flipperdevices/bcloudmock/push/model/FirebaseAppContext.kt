package com.flipperdevices.bcloudmock.push.model

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions

interface FirebaseAppContext {
    val googleCredentials: GoogleCredentials
    val options: FirebaseOptions
    val firebaseApp: FirebaseApp
}
