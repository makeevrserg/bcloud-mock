plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    implementation("com.h2database:h2:2.2.224")
    implementation(libs.exposed.javatime)
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.core)
}
