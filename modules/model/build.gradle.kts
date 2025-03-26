plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization")
}

dependencies {
    // Serialization
    implementation(libs.kotlin.serialization.json)
    // Services
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.busycloud)
}
