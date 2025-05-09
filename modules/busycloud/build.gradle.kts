plugins {
    id("org.jetbrains.kotlin.jvm")
    kotlin("plugin.serialization")
}

dependencies {
    libs.versions.ktor.core.get().let { ktor ->
        implementation("io.ktor:ktor-client-core:$ktor")
        implementation("io.ktor:ktor-client-java:$ktor")
        implementation("io.ktor:ktor-client-content-negotiation:$ktor")
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")
        implementation("io.ktor:ktor-client-logging:$ktor")
    }
    implementation(libs.kotlin.datetime)
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.core)
}
