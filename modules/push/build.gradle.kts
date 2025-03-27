plugins {
    id("org.jetbrains.kotlin.jvm")
}

dependencies {
    implementation("com.google.auth:google-auth-library-oauth2-http:1.19.0")
    implementation("com.google.firebase:firebase-admin:9.3.0")
    // Local
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.core)
}
