import ru.astrainteractive.gradleplugin.property.extension.ModelPropertyValueExt.requireProjectInfo

plugins {
    id("org.jetbrains.kotlin.jvm")
    application
    kotlin("plugin.serialization")
    alias(libs.plugins.gradle.shadow)
}

dependencies {
    // Ktor
    libs.versions.ktor.core.get().let { ktor ->
        implementation("io.ktor:ktor-serialization-kotlinx-json:$ktor")
        implementation("io.ktor:ktor-server-content-negotiation:$ktor")
        implementation("io.ktor:ktor-server-core:$ktor")
        implementation("io.ktor:ktor-server-netty:$ktor")
        implementation("io.ktor:ktor-server-status-pages:$ktor")
        implementation("io.ktor:ktor-server-auth:$ktor")
        implementation("io.ktor:ktor-server-auth-jwt:$ktor")
        implementation("io.ktor:ktor-server-core:$ktor")
        implementation("io.ktor:ktor-server-cors:$ktor")
        implementation("io.ktor:ktor-server-resources:$ktor")
        implementation("io.ktor:ktor-server-content-negotiation:$ktor")
        implementation("io.ktor:ktor-server-websockets:$ktor")
    }
    implementation(libs.smiley4.ktor.swagger)
    implementation(libs.smiley4.ktor.openai)
    libs.versions.smiley4.kenerator.get().let { version ->
        implementation("io.github.smiley4:schema-kenerator-core:$version")
        implementation("io.github.smiley4:schema-kenerator-reflection:$version")
        implementation("io.github.smiley4:schema-kenerator-serialization:$version")
        implementation("io.github.smiley4:schema-kenerator-swagger:$version")
        implementation("io.github.smiley4:schema-kenerator-jackson:$version")
    }
    // Serialization
    implementation(libs.kotlin.serialization.json)
    implementation(libs.kotlin.datetime)
    // Log
    implementation(libs.logback.classic)
    // Exposed
    implementation(libs.exposed.core)
    implementation(libs.exposed.dao)
    // test
    testImplementation(kotlin("test"))
    // Services
    implementation(projects.modules.buildKonfig)
    implementation(projects.modules.core)
    implementation(projects.modules.model)
    implementation(projects.modules.dao)
    implementation(projects.modules.data)
    implementation(projects.modules.busycloud)
    implementation(projects.modules.push)
}

application {
    mainClass.set("com.flipperdevices.bcloudmock.MainKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

tasks.shadowJar {
    isReproducibleFileOrder = true
    mergeServiceFiles()
    dependsOn(configurations)
    archiveClassifier.set(null as String?)
    from(sourceSets.main.get().output)
    from(project.configurations.runtimeClasspath)

    archiveVersion.set(requireProjectInfo.versionString)
    archiveBaseName.set("${requireProjectInfo.name}-web")
    File(rootDir, "jars").also(File::mkdirs).also(destinationDirectory::set)
}
