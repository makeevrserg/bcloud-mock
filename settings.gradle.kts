pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
    }
}

dependencyResolutionManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        mavenLocal()
        google()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
rootProject.name = "backend"

include(":modules:build-konfig")
include(":modules:core")
include(":modules:data")
include(":modules:busycloud")
include(":modules:dao")
include(":modules:model")
include(":modules:push")
include(":web-api")
