plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization")
}

dependencies {
    // Serialization
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.datetime)
    // Services
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.busycloud)
}
