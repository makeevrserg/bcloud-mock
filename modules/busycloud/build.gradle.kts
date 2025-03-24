plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    libs.versions.ktor.core.get().let { ktor ->
        implementation("io.ktor:ktor-client-core:${ktor}")
        implementation("io.ktor:ktor-client-cio:${ktor}")
        implementation("io.ktor:ktor-client-content-negotiation:${ktor}")
        implementation("io.ktor:ktor-serialization-kotlinx-json:${ktor}")
    }
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.core)
}