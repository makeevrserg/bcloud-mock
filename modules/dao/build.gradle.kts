plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation("com.h2database:h2:2.2.224")
    implementation(libs.exposed.javatime)
    implementation(libs.kotlin.datetime)
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.core)
    implementation(projects.modules.data)
    implementation(projects.modules.busycloud)
}
